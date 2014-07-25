/**
 * 
 */
package es.rikimred.incost.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;

import es.rikimred.incost.entity.Book;
import es.rikimred.incost.global.Global;
import es.rikimred.incost.global.GlobalBook;
import es.rikimred.incost.model.BalanceData;

/**
 * @author jrneyra
 * 
 */
@Named
@SessionScoped
public class ChartController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private transient Logger logger;

	// Book seleccionado
	private Book selectedBook;

	private CartesianChartModel model;

	public ChartController() {
	}

	@PostConstruct
	private void initialize() {
		logger.info("Inicializando charts");
		model = new CartesianChartModel();
	}

	/**
	 * 
	 */
	public void doChart(final ActionEvent actionEvent) {
		if (selectedBook != null) {
			model = new CartesianChartModel();
			ChartSeries balanceSeries = new ChartSeries("Balances");

			List<BalanceData> balanceList = Global.bookBalanceListRetrive(selectedBook.getId());
			for (BalanceData b : balanceList) {
				balanceSeries.set(b.getYearMonth(), b.getBalanceResult());
			}

			model.addSeries(balanceSeries);
		}
	}

	/**
	 * @return the selectedBook
	 */
	public Book getSelectedBook() {
		return selectedBook;
	}

	/**
	 * @param selectedBook the selectedBook to set
	 */
	public void setSelectedBook(Book selectedBook) {
		this.selectedBook = selectedBook;
	}

	/**
	 * 
	 * @return
	 */
	public List<Book> getBookList() {
		return GlobalBook.instance().entityList();
	}

	public CartesianChartModel getModel() {
		logger.info("Recuperando el model");
		return model;
	}
}
