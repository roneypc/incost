package es.rikimred.incost.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import es.rikimred.incost.entity.Category;
import es.rikimred.incost.entity.FixedData;
import es.rikimred.incost.entity.User;
import es.rikimred.incost.enums.CategoryTypeEnum;
import es.rikimred.incost.enums.SessionAttributesEnum;
import es.rikimred.incost.global.Global;
import es.rikimred.incost.global.GlobalCategory;
import es.rikimred.incost.global.GlobalFixedBudget;
import es.rikimred.incost.global.GlobalFixedCash;
import es.rikimred.incost.global.GlobalFixedExpenses;
import es.rikimred.incost.global.GlobalFixedIncome;
import es.rikimred.incost.model.CategoryData;
import es.rikimred.incost.model.FixedDataModel;
import es.rikimred.incost.service.BalanceService;
import es.rikimred.incost.service.CategoryService;
import es.rikimred.incost.service.FixedDataService;
import es.rikimred.incost.service.UserService;
import es.rikimred.incost.util.MiscUtils;
import es.rikimred.incost.util.SessionUtils;

/**
 * Autenticacion de usuarios para acceso a la aplicacion
 * @author roberto
 */
@Named
@SessionScoped
public class LoginController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private transient Logger logger;

	@Inject
	private BalanceController balanceController;

	@Inject
	private ViewController viewController;

	@Inject
	private BalanceService balanceService;

	@Inject
	private UserService userService;

	@Inject
	private CategoryService categoryService;

	@Inject
	private FixedDataService fixedDataService;

	private String login = "roney";

	private String password;

	private String message;

	/**
	 * Creates a new instance of LoginController
	 */
	public LoginController() {
	}

	/**
	 * Validacion de acceso
	 * @return
	 */
	public void loginApp() {
		final HttpServletRequest request = SessionUtils.getRequest();
		final HttpSession session = request.getSession();
		String message;
		try {
			String navigateString = "";
			// Comprueba si login y password son válidos, sino lanza una excepción
			request.login(login, password);
			// Obtener los datos del usuario
			final User user = userService.findUserByLoginAndPassword(login, MiscUtils.encode(password));
			// Guardar los datos del usuario en la sesion
			Global.setActiveUser(user);
			session.setAttribute(SessionAttributesEnum.USERNAME.getName(), user.getName());
			// Validar el rol del usuario para redirigirle a su pagina
			if (request.isUserInRole("Administrator")) {
				logger.info("Se ha iniciado sesion como administrador");
				navigateString = "/admin/AdminHome.xhtml";
			}
			else if (request.isUserInRole("User")) {
				logger.info("Se ha iniciado sesion como usuario: " + user.getName());
				navigateString = "/user/balance.xhtml?faces-redirect=true";
			}

			// Cargar las categorías globales antes de redigir a la pagina del usuario
			loadCategories();

			// Colocar el menu tab en balances
			viewController.setActiveIndexes("1");

			try {
				SessionUtils.getExternalContext().redirect(request.getContextPath() + navigateString);
			}
			catch (final IOException e) {
				message = String.format("Error de navegacion");
				logger.error(message);
				SessionUtils.getContext().addMessage(null, new FacesMessage("Error!", message));
			}
		}
		catch (final ServletException e) {
			message = String.format("Error de acceso [%s]", login);
			logger.error(message);
			SessionUtils.getContext().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Intente nuevamente", message));
		}
	}

	/**
	 * Cerrar la sesion
	 * @return
	 */
	public String logout() {
		logger.info("Se ha cerrado la sesion");
		// Guardar si hay un balance cargado
		balanceController.saveBalances();
		final HttpSession session = SessionUtils.getSession();
		session.invalidate();
		Global.terminate();
		return "/login.xhtml?faces-redirect=true";
	}

	/* GETTERS & SETTERS */

	/**
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * @param login the login to set
	 */
	public void setLogin(final String login) {
		this.login = login;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(final String password) {
		this.password = password;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(final String message) {
		this.message = message;
	}

	/**
	 * 
	 */
	private void loadCategories() {
		// Recuperar las categorías del usuario
		final List<Category> categoryList = categoryService.getCategoriesByIdUser(Global.activeUser().getId());
		// Limpiar el mapa de categories
		GlobalCategory.instance().clear();
		// Construir el mapa de categories
		for (final Category category : categoryList) {
			// Si la categoría es del tipo incomes o expenses
			if (CategoryTypeEnum.CATEGORY_INCOMES.equals(category.getCategoryType())
					|| CategoryTypeEnum.CATEGORY_EXPENSES.equals(category.getCategoryType())) {
				GlobalCategory.instance().put(new CategoryData(category));
			}
			// Si la categoría es del tipo fixed income
			if (CategoryTypeEnum.FIXED_INCOME.equals(category.getCategoryType())) {
				GlobalFixedIncome.instance().setFixedIncomeCategory(category);
			}
			// Si la categoría es del tipo fixed expenses
			if (CategoryTypeEnum.FIXED_EXPENSES.equals(category.getCategoryType())) {
				GlobalFixedExpenses.instance().setFixedExpensesCategory(category);
			}
			// Si la categoría es del tipo fixed cash
			if (CategoryTypeEnum.FIXED_CASH.equals(category.getCategoryType())) {
				GlobalFixedCash.instance().setFixedCashCategory(category);
			}
		}

		// Limpiar el mapa de fixed incomes
		GlobalFixedIncome.instance().clear();
		// Construir el mapa de fixed incomes
		List<FixedData> fixedDataList =
				fixedDataService
						.getFixedDataByIdCategory(GlobalFixedIncome.instance().getFixedIncomeCategory().getId());
		for (final FixedData f : fixedDataList) {
			GlobalFixedIncome.instance().put(new FixedDataModel(f));
		}

		// Limpiar el mapa de fixed budget
		GlobalFixedBudget.instance().clear();
		// Construir el mapa de fixed budget
		for (final Category c : GlobalCategory.instance().entityList(CategoryTypeEnum.CATEGORY_EXPENSES)) {
			// Recuperar los fixed budgets
			fixedDataList = fixedDataService.getFixedDataByIdCategory(c.getId());
			for (final FixedData f : fixedDataList) {
				GlobalFixedBudget.instance().put(new FixedDataModel(f));
			}
		}

		// Limpiar el mapa de fixed expenses
		GlobalFixedExpenses.instance().clear();
		// Construir el mapa de fixed expenses
		fixedDataList =
				fixedDataService.getFixedDataByIdCategory(GlobalFixedExpenses.instance().getFixedExpensesCategory()
						.getId());
		for (final FixedData f : fixedDataList) {
			GlobalFixedExpenses.instance().put(new FixedDataModel(f));
		}

		// Limpiar el mapa de fixed cash
		GlobalFixedCash.instance().clear();
		// Construir el mapa de fixed cash
		fixedDataList =
				fixedDataService.getFixedDataByIdCategory(GlobalFixedCash.instance().getFixedCashCategory().getId());
		for (final FixedData f : fixedDataList) {
			GlobalFixedCash.instance().put(new FixedDataModel(f));
		}
	}
}
