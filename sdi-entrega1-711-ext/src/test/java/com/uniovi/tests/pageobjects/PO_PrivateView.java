package com.uniovi.tests.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.uniovi.tests.util.SeleniumUtils;

public class PO_PrivateView extends PO_NavView {

	static public void fillFormAddMark(WebDriver driver, int userOrder, String descriptionp, String scorep) {
		// Esperamos 5 segundo a que carge el DOM porque en algunos equipos falla
		SeleniumUtils.esperarSegundos(driver, 5);
		// Seleccionamos el alumnos userOrder
		new Select(driver.findElement(By.id("user"))).selectByIndex(userOrder);
		// Rellenemos el campo de descripción
		WebElement description = driver.findElement(By.name("description"));
		description.clear();
		description.sendKeys(descriptionp);
		WebElement score = driver.findElement(By.name("score"));
		score.click();
		score.clear();
		score.sendKeys(scorep);
		By boton = By.className("btn");
		driver.findElement(boton).click();
	}

	static public void login(WebDriver driver, String dni, String contraseña, String text) {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, dni, contraseña);
		// COmprobamos que entramos en la pagina privada de Alumno
		PO_View.checkElement(driver, "text", text);
	}

	static public void logout(WebDriver driver, String text) {
		PO_PrivateView.clickOptionLogout(driver, "logout", "text", text);
	}

	static public void lastPage(WebDriver driver) {
		List<WebElement> elementos;
		// Esperamos a que se muestren los enlaces de paginación la lista de notas
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@class, 'page-link')]");
		// Nos vamos a la última página
		elementos.get(elementos.size() - 1).click();
	}
	
	static public void checkChangeIdiom(WebDriver driver, String textIdiom1, String textIdiom2, int locale1, int locale2) {
		// Esperamos a que se cargue el saludo de bienvenida en Español
		PO_PrivateView.checkList(driver, locale1);
		// Cambiamos a segundo idioma
		PO_HomeView.changeIdiom(driver, textIdiom2);
		// COmprobamos que el texto de bienvenida haya cambiado a segundo idioma
		PO_PrivateView.checkList(driver, locale2);
		// Volvemos a Español.
		PO_HomeView.changeIdiom(driver, textIdiom1);
		// Esperamos a que se cargue el saludo de bienvenida en Español
		PO_PrivateView.checkList(driver, locale1);
	}
	
	static public void checkList(WebDriver driver, int language) {
		//Esperamos a que se cargue el saludo de bienvenida en Español
		SeleniumUtils.EsperaCargaPagina(driver, "text", p.getString("user_list_descr.message", 
		language), getTimeout());
		
	}
}
