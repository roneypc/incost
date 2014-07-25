/**
 * 
 */
package es.rikimred.incost.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import es.rikimred.incost.entity.Balance;
import es.rikimred.incost.entity.Book;
import es.rikimred.incost.global.Global;
import es.rikimred.incost.global.GlobalBalance;
import es.rikimred.incost.global.GlobalBook;
import es.rikimred.incost.lazy.LazyBookDataModel;
import es.rikimred.incost.model.BalanceData;
import es.rikimred.incost.model.BookData;
import es.rikimred.incost.service.BalanceService;
import es.rikimred.incost.service.BookService;

/**
 * Controlador de 'book.xhtml'
 * @author jrneyra
 */
@Named
@SessionScoped
public class BookController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private transient Logger logger;

	@Inject
	private BookService bookService;

	@Inject
	private BalanceService balanceService;

	// Lazy loading item list
	private LazyBookDataModel lazyModel;

	// Creating new item
	private BookData newItem;

	// Selected item that will be updated
	private BookData selectedItem;

	private BalanceData selectedBalance;

	private Balance[] balanceArray;

	/**
	 * 
	 */
	public BookController() {
	}

	@PostConstruct
	private void initialize() {
		logger.info("Inicializando books");
		newItem = new BookData(new Book());
		doUpdateTable();
	}

	/**
	 * Actualiza la tabla de books
	 */
	public void doUpdateTable() {
		logger.info("Actualizando tabla books");
		lazyModel = new LazyBookDataModel();
	}

	/**
	 * Create, Update and Delete operations
	 */
	public void doCreateItem() {
		logger.info("Se procede a crear el book " + newItem.getDescription());
		Book entity = newItem.getEntity();
		entity.setUser(Global.activeUser());
		entity.setBookBalance(BigDecimal.ZERO);
		entity.setTotalBudget(BigDecimal.ZERO);
		entity.setTotalIncome(BigDecimal.ZERO);
		entity.setTotalExpenses(BigDecimal.ZERO);
		entity = bookService.create(entity);

		// Actualizar el mapa de books
		GlobalBook.instance().put(new BookData(entity));

		logger.info("Se ha creado el book " + newItem.getDescription());
		newItem = new BookData(new Book());
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Libro creado", "Libro creado correctamente"));
	}

	/**
	 * 
	 * @param actionEvent
	 */
	public void doUpdateItem(final ActionEvent actionEvent) {
		Book entity = bookService.update(selectedItem.getEntity());
		// Actualizar el mapa de book
		GlobalBook.instance().put(new BookData(entity));

		logger.info(String.format("El book [%d] ha sido actualizado", selectedItem.getId()));
	}

	/**
	 * 
	 * @param actionEvent
	 */
	public void doDeleteItem(final ActionEvent actionEvent) {
		// Si se ha seleccionado un elemento
		if (selectedItem != null) {
			// Validar si no existen balances
			List<Balance> balanceList = balanceService.getBalancesByIdBook(selectedItem.getId());
			if (balanceList == null || balanceList.isEmpty()) {
				GlobalBook.instance().remove(selectedItem.getId());
				bookService.delete(selectedItem.getId());
			}
			else {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Imposible eliminar",
								"El libro aún contiene balances. Elimine éstos primero."));
			}
		}
		else {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sin selección",
							"Debe seleccionar un libro para eliminar."));
		}
	}

	public void doDeleteBalance() {
		logger.info("Se procede a eliminar el balance del book");
		if (selectedBalance != null) {
			// Se remueve el balance del mapa
			Global.bookBalanceRemove(selectedItem.getId(), selectedBalance);
			// Se obtiene la nueva lista
			List<BalanceData> balanceList = Global.bookBalanceListRetrive(selectedItem.getId());
			// Creación de la lista de entities
			List<Balance> balanceEntityList = new ArrayList<Balance>();
			for (BalanceData b : balanceList) {
				balanceEntityList.add(b.getEntity());
			}
			// Recuperación del entity
			Book entity = selectedItem.getEntity();
			// Set de la nueva lista de balances
			entity.setBalances(balanceEntityList);
			// Actualización del book en BBDD
			bookService.update(entity);

			// Actualización del book
			updateBookItem(selectedItem);

			// Eliminar la selección
			selectedBalance = null;
		}
	}

	public void doAddBalance() {
		logger.info("Se procede a agregar balances al book");
		if (balanceArray != null && balanceArray.length > 0) {
			Book entity = selectedItem.getEntity();
			List<Balance> balanceList = balanceService.getBalancesByIdBook(entity.getId());
			for (Balance b : balanceArray) {
				balanceList.add(b);
				// Agregar al mapa de book balance
				Global.bookBalancePut(entity.getId(), GlobalBalance.instance().retrive(b.getId()));
			}
			entity.setBalances(balanceList);
			entity = bookService.update(entity);

			// Actualizar el mapa de books
			GlobalBook.instance().put(new BookData(entity));

			// Actualizar el book
			updateBookItem(selectedItem);

			String message = String.format("Se ha(n) agregado [%d] balance(s) correctamente", balanceArray.length);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Balance(s) agregado(s)", message));
		}
	}

	/**
	 * @return the lazyModel
	 */
	public LazyBookDataModel getLazyModel() {
		return lazyModel;
	}

	/**
	 * @return the newItem
	 */
	public BookData getNewItem() {
		return newItem;
	}

	public void setNewItem(final BookData newItem) {
		this.newItem = newItem;
	}

	/**
	 * @return the selectedItem
	 */
	public BookData getSelectedItem() {
		return selectedItem;
	}

	/**
	 * @return the selectedBalance
	 */
	public BalanceData getSelectedBalance() {
		return selectedBalance;
	}

	/**
	 * @param selectedBalance the selectedBalance to set
	 */
	public void setSelectedBalance(BalanceData selectedBalance) {
		this.selectedBalance = selectedBalance;
	}

	public void setSelectedItem(final BookData selectedItem) {
		this.selectedItem = selectedItem;
	}

	/**
	 * @return the balanceArray
	 */
	public Balance[] getBalanceArray() {
		return balanceArray;
	}

	/**
	 * @param balanceArray the balanceArray to set
	 */
	public void setBalanceArray(Balance[] balanceArray) {
		this.balanceArray = balanceArray;
	}

	/**
	 * 
	 * @return
	 */
	public List<BalanceData> getBookBalanceList() {
		if (selectedItem != null) {
			return Global.bookBalanceListRetrive(selectedItem.getId());
		}
		return new ArrayList<BalanceData>();
	}

	/**
	 * Construir la lista de balances suceptibles de ser agregados
	 * @return
	 */
	public List<Balance> getAddBalanceList() {
		List<Balance> balanceList = new ArrayList<Balance>();
		if (selectedItem != null) {
			List<Book> bookList = new ArrayList<Book>();
			for (BalanceData b : GlobalBalance.instance().list()) {
				// Flag no se ha encontrado
				boolean founded = false;
				// Recuperar la lista de books a las que pertenece el balance
				bookList = bookService.getBooksByIdBalance(b.getId());
				for (Book book : bookList) {
					// Si el book seleccionado ya pertenece al balance
					if (selectedItem.getId().compareTo(book.getId()) == 0) {
						// Flag se ha encontrado
						founded = true;
						// Detener el bucle
						break;
					}
				}
				// Si el balance no ha sido agregado ya al book
				if (!founded) {
					// Agregarlo a la lista
					balanceList.add(b.getEntity());
				}
			}
		}
		return balanceList;
	}

	/**
	 *
	 */
	public void updateBookItem(BookData bookData) {
		BigDecimal totalIncome = BigDecimal.ZERO;
		BigDecimal totalBudget = BigDecimal.ZERO;
		BigDecimal totalExpenses = BigDecimal.ZERO;
		BigDecimal bookBalance = BigDecimal.ZERO;

		// Obtener los balances del book
		List<BalanceData> balanceList = Global.bookBalanceListRetrive(bookData.getId());
		for (BalanceData b : balanceList) {
			totalIncome = totalIncome.add(b.getTotalIncome());
			totalBudget = totalBudget.add(b.getTotalBudget());
			totalExpenses = totalExpenses.add(b.getTotalExpenses());
			bookBalance = bookBalance.add(b.getBalanceResult());
		}

		bookData.setTotalIncome(totalIncome);
		bookData.setTotalBudget(totalBudget);
		bookData.setTotalExpenses(totalExpenses);
		bookData.setBookBalance(bookBalance);

		// Actualizar en BBDD
		bookService.update(bookData.getEntity());

		// Volverlo a guardar en el mapa
		GlobalBook.instance().put(bookData);
	}
}
