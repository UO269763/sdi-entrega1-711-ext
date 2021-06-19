package com.uniovi;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.annotation.Autowired;

import com.uniovi.entities.User;
import com.uniovi.services.UsersService;
import com.uniovi.tests.pageobjects.PO_AgregarOfertaView;
import com.uniovi.tests.pageobjects.PO_HomeView;
import com.uniovi.tests.pageobjects.PO_LoginView;
import com.uniovi.tests.pageobjects.PO_NavView;
import com.uniovi.tests.pageobjects.PO_PrivateView;
import com.uniovi.tests.pageobjects.PO_Properties;
import com.uniovi.tests.pageobjects.PO_RegisterView;
import com.uniovi.tests.pageobjects.PO_View;
import com.uniovi.tests.util.SeleniumUtils;

//Ordenamos las pruebas por el nombre del método
//@FixMethodOrder(MethodSorters.JVM)

public class SdiEntrega1711ExtApplicationTests {
	

	// En Windows (Debe ser la versión 65.0.1 y desactivar las actualizacioens
	// automáticas)):
	static String PathFirefox65 = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
	// static String PathFirefox65 = "C:\\Program Files\\Mozilla
	// Firefox\\firefox.exe";
	//static String Geckdriver024 = "C:\\Users\\santi\\Desktop\\Informatica\\Tercero\\SegundoCuatri\\SDI\\PL-SDI-Sesión5-material\\geckodriver024win64.exe";
	static String Geckdriver024 = "C:\\Users\\Usuario\\Desktop\\TERCEROINFORMATICA\\SEGUNDOSEMESTRE\\SDI\\lab\\PL-SDI-Sesión5-material\\PL-SDI-Sesión5-material\\geckodriver024win64.exe";
	// En MACOSX (Debe ser la versión 65.0.1 y desactivar las actualizacioens
	// automáticas):
	// static String PathFirefox65 =
	// "/Applications/Firefox.app/Contents/MacOS/firefox-bin";
	// static String Geckdriver024 = "/Users/delacal/selenium/geckodriver024mac";
	// Común a Windows y a MACOSX
	static WebDriver driver = getDriver(PathFirefox65, Geckdriver024);
	static String URL = "http://localhost:8090";

	public static WebDriver getDriver(String PathFirefox, String Geckdriver) {
		System.setProperty("webdriver.firefox.bin", PathFirefox);
		System.setProperty("webdriver.gecko.driver", Geckdriver);
		WebDriver driver = new FirefoxDriver();
		return driver;
	}

	@Before
	public void setUp() {
		driver.navigate().to(URL);
	}

	@After
	public void tearDown() {
		driver.manage().deleteAllCookies();
	}

	@BeforeClass
	static public void begin() {
	}

	@AfterClass
	static public void end() {
		driver.quit();
	}

	// 1.REGISTRO DEL USUARIO CON DATOS VÁLIDOS
	@Test
	public void PR01() {
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "pedro@gmail.com", "Pedro", "Garcia", "123456", "123456");
		// Comprobamos que entramos en la sección privada
		PO_View.checkElement(driver, "text", "pedro@gmail.com");
	}

	// 2.REGISTRO DE USUARIO CON DATOS INVÁLIDOS (EMAIL VACÍO, NOMBRE VACÍO,
	// APELLIDOS VACÍOS)
	@Test
	public void PR02() {
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "", "Pedro", "Garcia", "123456", "123456");
		PO_View.getP();
		// Comprobamos que no se redirige a la vista privada
		PO_RegisterView.checkElement(driver, "text", "Regístrate");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "pedro@gmail.com", "", "Garcia", "123456", "123456");
		// Comprobamos que no se redirige a la vista privada
		PO_RegisterView.checkElement(driver, "text", "Regístrate");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "pedro@gmail.com", "Pedro", "", "123456", "123456");
		// Comprobamos el error de apellido vacio.
		// Comprobamos que no se redirige a la vista privada
		PO_RegisterView.checkElement(driver, "text", "Regístrate");
	}

	// 3.REGISTRO DEL USUARIO CON DATOS INVÁLIDOS (REPETICIÓN DE LA CONTRASEÑA MAL)
	@Test
	public void PR03() {
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "kike@gmail.com", "Kike", "Perez", "123456", "123456789");
		// Comprobamos el error de la contraseña no coincide.
		PO_RegisterView.checkKey(driver, "Error.signup.passwordConfirm.coincidence", PO_Properties.getSPANISH());

	}

	// 4.REGISTRO DEL USUARIO CON DATOS INVÁLIDOS (EMAIL EXISTENTE)
	// @Test
	public void PR04() {
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "lucas@email.es", "Josefo", "Perez", "77777", "77777");
		PO_View.getP();
		// Comprobamos el error de DNI repetido.
		PO_RegisterView.checkKey(driver, "Error.signup.error.duplicate", PO_Properties.getSPANISH());

	}

	// 5.INICIO DE SESIÓN CON DATOS VÁLIDOS (ADMIN).
	@Test
	public void PR05() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "admin@email.es", "123456");
		// COmprobamos que entramos en la pagina privada de Admin
		PO_View.checkElement(driver, "text", "Gestión de usuarios");
	}

	// 6.INICIO DE SESIÓN CON DATOS VÁLIDOS (USUARIO ESTÁNDAR)
	@Test
	public void PR06() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "lucas@email.es", "123456");
		// COmprobamos que entramos en la pagina privada de usuario
		PO_View.checkElement(driver, "text", "Gestión de ofertas");
	}

	// 7.INICIO DE SESIÓN CON DATOS INVÁLIDOS (USUARIO ESTÁNDAR, CAMPO EMAIL Y
	// CONTRASEÑA VACÍO)
	@Test
	public void PR07() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "", "");
		// Comprobamos que seguimos en la página de logueo
		PO_View.checkElement(driver, "text", "Identifícate");
	}

	// 8.INICIO DE SESIÓN CON DATOS VÁLIDOS (USUARIO ESTÁNDAR, EMAIL EXISTENTE, PERO
	// CONTRASEÑA INCORRECTA)
	@Test
	public void PR08() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "lucas@email.es", "123456789");
		// Comprobamos que seguimos en la página de logueo
		PO_View.checkElement(driver, "text", "Identifícate");
	}

	// 9.INICIO DE SESIÓN CON DATOS INVÁLIDOS (USUARIO ESTÁNDAR, EMAIL NO EXISTENTE
	// EN LA APLICACIÓN)
	@Test
	public void PR09() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "prueba@email.es", "123456789");
		// Comprobamos que seguimos en la página de logueo
		PO_View.checkElement(driver, "text", "Identifícate");
	}

	// 10.HACER CLICK EN LA OPCIÓN DE SALIR SESIÓN Y COMPROBAR QUE SE REDIRIGE A LA
	// PÁGINA DE INICIO
	@Test
	public void PR10() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "admin@email.es", "123456");
		// COmprobamos que entramos en la pagina privada de usuario
		PO_View.checkElement(driver, "text", "Gestión de ofertas");
		// Ahora nos desconectamos
		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
		// PO_PrivateView.logOut(driver, "Desconectar");
		PO_View.checkElement(driver, "text", "Identifícate");
	}

	// 11.COMPROBAR QUE EL BOTÓN DE CERRAR SESIÓN NO ESTÁ VISIBLE PARA UN USUARIO NO
	// AUTENTICADO
	@Test
	public void PR11() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Comprobamos que en home sin estar logeado no aparece la opcion de cerrar
		// sesion (desconectar)
		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "Desconectar", PO_View.getTimeout());
	}

	// 12. Mostrar el listado de usuarios y comprobar que se muestran todos los que
	// existen en el
	// sistema.
	//NO FUNCIONA
	@Test
	public void PR12() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "admin@email.es", "123456");
		// COmprobamos que entramos en la pagina privada de usuario
		PO_View.checkElement(driver, "text", "Gestión de ofertas");
		// Pinchamos la opcion de menu de usuario
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'users-menu')]/a");
		elementos.get(0).click();
		// Buscamos la opcion de ver lista de usuario
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'user/list')]");
		elementos.get(0).click();
		// Contamos el número de filas de notas
		List<WebElement> numElementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr",
				PO_View.getTimeout());
		// COMPROBAR NUMERO QUE VAMOS A PONER
		assertTrue(numElementos.size() == 7);
	}

	// 16.Ir al formulario de alta de oferta, rellenarla con datos válidos y pulsar
	// el botón Submit.
	// Comprobar que la oferta sale en el listado de ofertas de dicho usuario
	@Test
	public void PR16() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "pedro@email.es", "123456");
		// COmprobamos que entramos en la pagina privada de usuario
		PO_View.checkElement(driver, "text", "Gestión de ofertas");
		// Pinchamos la opcion de menu de usuario
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'ofertas-menu')]/a");
		elementos.get(0).click();
		// Buscamos la opcion de ver lista de usuario
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'oferta/add')]");
		elementos.get(0).click();
		// Rellenamos el formulario para subir la oferta
		PO_AgregarOfertaView.fillForm(driver, "Sudadera", "Talla M, roja", "20");
		PO_View.checkElement(driver, "text", "Sudadera");
	}

	// 17. Ir al formulario de alta de oferta, rellenarla con datos inválidos (campo
	// título vacío) y pulsar
	// el botón Submit. Comprobar que se muestra el mensaje de campo obligatorio.
	@Test
	public void PR17() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "pedro@email.es", "123456");
		// COmprobamos que entramos en la pagina privada de usuario
		PO_View.checkElement(driver, "text", "Gestión de ofertas");
		// Pinchamos la opcion de menu de usuario
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'ofertas-menu')]/a");
		elementos.get(0).click();
		// Buscamos la opcion de ver lista de usuario
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'oferta/add')]");
		elementos.get(0).click();
		// Rellenamos el formulario con el titulo vacio
		PO_AgregarOfertaView.fillForm(driver, "", "Talla M, roja", "20");
		// Comprobamos que seguimos en la pagina
		PO_View.checkElement(driver, "text", "Titulo:");
		// Rellenamos el formulario con la descripcion vacia
		PO_AgregarOfertaView.fillForm(driver, "Camiseta", "", "20");
		// Comprobamos que seguimos en la pagina
		PO_View.checkElement(driver, "text", "Titulo:");
		// Rellenamos el formulario con el precio vacio
		PO_AgregarOfertaView.fillForm(driver, "Camiseta", "Talla M, roja", "");
		// Comprobamos que seguimos en la pagina
		PO_View.checkElement(driver, "text", "Titulo:");
	}

	// 18. Mostrar el listado de ofertas para dicho usuario y comprobar que se
	// muestran todas los que
	// existen para este usuario.
	// 16.Ir al formulario de alta de oferta, rellenarla con datos válidos y pulsar
	// el botón Submit.
	// Comprobar que la oferta sale en el listado de ofertas de dicho usuario
	@Test
	public void PR18() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "pedro@email.es", "123456");
		// COmprobamos que entramos en la pagina privada de usuario
		PO_View.checkElement(driver, "text", "Gestión de ofertas");
		// Pinchamos la opcion de menu de usuario
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'ofertas-menu')]/a");
		elementos.get(0).click();
		// Buscamos la opcion de ver lista de usuario
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'oferta/list')]");
		elementos.get(0).click();
		// Comprobamos que tenga sus ofertas.

		// FALTA INICIALIZAR LA BASE CON ESTAS OFERTAS

		PO_View.checkElement(driver, "text", "Camiseta azul");
		PO_View.checkElement(driver, "text", "Sudadera azul");
		PO_View.checkElement(driver, "text", "Pantalon azul");
	}

	// 19. Ir a la lista de ofertas, borrar la primera oferta de la lista, comprobar
	// que la lista se actualiza y
	// que la oferta desaparece.
	//NO FUNCIONA
	@Test
	public void PR19() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "pedro@email.es", "123456");
		// COmprobamos que entramos en la pagina privada de usuario
		PO_View.checkElement(driver, "text", "Gestión de ofertas");
		// Pinchamos la opcion de menu de usuario
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'ofertas-menu')]/a");
		elementos.get(0).click();
		// Buscamos la opcion de ver lista de usuario
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'oferta/list')]");
		elementos.get(0).click();
		// Comprobamos que tenga sus ofertas.

		// FALTA INICIALIZAR LA BASE CON ESTAS OFERTAS

		PO_View.checkElement(driver, "text", "Camiseta azul");

		// Eliminamos la primera
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'oferta/delete/8')]");
		elementos.get(0).click();
		// Comprobamos que no se encuentre
		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "Camiseta azul", PO_View.getTimeout());
	}

	// ECHAR UN OJO QUE ID TIENE EL ULTIMO CUANDO CREEMOS LA BASE
	//NO FUNCIONA
	@Test
	public void PR20() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "pedro@email.es", "123456");
		// COmprobamos que entramos en la pagina privada de usuario
		PO_View.checkElement(driver, "text", "Gestión de ofertas");
		// Pinchamos la opcion de menu de usuario
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'ofertas-menu')]/a");
		elementos.get(0).click();
		// Buscamos la opcion de ver lista de usuario
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'oferta/list')]");
		elementos.get(0).click();
		// Comprobamos que tenga sus ofertas.

		// FALTA INICIALIZAR LA BASE CON ESTAS OFERTAS

		PO_View.checkElement(driver, "text", "Pantalon azul");

		// Eliminamos la primera
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'oferta/delete/9')]");
		elementos.get(0).click();
		// Comprobamos que no se encuentre
		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "Sudadera azul", PO_View.getTimeout());
	}

	// 21. Hacer una búsqueda con el campo vacío y comprobar que se muestra la
	// página que
	// corresponde con el listado de las ofertas existentes en el sistema
	@Test
	public void PR21() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "pedro@email.es", "123456");
		// COmprobamos que entramos en la pagina privada de usuario
		PO_View.checkElement(driver, "text", "Gestión de ofertas");
		// Pinchamos la opcion de menu de usuario
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'ofertas-menu')]/a");
		elementos.get(0).click();
		// Buscamos la opcion de ver lista de usuario
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'oferta/search')]");
		elementos.get(0).click();
		// Comprobamos que tenga sus ofertas.
		WebElement buscar = driver.findElement(By.name("searchText"));
		buscar.click();
		buscar.clear();
		buscar.sendKeys("");
		By boton = By.className("btn");
		driver.findElement(boton).click();
		// Contamos el número de filas de notas --> todos los existentes
		List<WebElement> numEementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr",
				PO_View.getTimeout());

		// Falta comprobar el numero de ofertas que vamos a poner
		assertTrue(numEementos.size() == 5);
	}

	// Hacer una búsqueda escribiendo en el campo un texto que no exista y comprobar
	// que se
	// muestra la página que corresponde, con la lista de ofertas vacía.
	@Test
	public void PR22() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "pedro@email.es", "123456");
		// COmprobamos que entramos en la pagina privada de usuario
		PO_View.checkElement(driver, "text", "Gestión de ofertas");
		// Pinchamos la opcion de menu de usuario
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'ofertas-menu')]/a");
		elementos.get(0).click();
		// Buscamos la opcion de ver lista de usuario
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'oferta/search')]");
		elementos.get(0).click();
		// Comprobamos que tenga sus ofertas.
		WebElement buscar = driver.findElement(By.name("searchText"));
		buscar.click();
		buscar.clear();
		buscar.sendKeys("Gorra");
		By boton = By.className("btn");
		driver.findElement(boton).click();
		// Comprobamos que no existe ningun boton enviar solicitud/enviada
		SeleniumUtils.textoNoPresentePagina(driver, "Sudadera");
	}

	// 23. Sobre una búsqueda determinada (a elección del desarrollador), comprar
	// una oferta que deja
	// un saldo positivo en el contador del comprador. Comprobar que el contador se
	// actualiza correctamente
	// en la vista del comprador.
	//NO FUNCIONA
	@Test
	public void PR23() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "pedro@email.es", "123456");
		// COmprobamos que entramos en la pagina privada de usuario
		PO_View.checkElement(driver, "text", "Gestión de ofertas");
		// Pinchamos la opcion de menu de usuario
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'ofertas-menu')]/a");
		elementos.get(0).click();
		// Buscamos la opcion de ver lista de usuario
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'oferta/search')]");
		elementos.get(0).click();
		// Comprobamos que tenga sus ofertas.
		WebElement buscar = driver.findElement(By.name("searchText"));
		buscar.click();
		buscar.clear();
		buscar.sendKeys("Gorra");
		By boton = By.className("btn");
		driver.findElement(boton).click();

		// Comprar una sudadera
		elementos = PO_View.checkElement(driver, "free", "//button[contains(text(), 'Comprar')]");
		elementos.get(0).click();
		URL = "http://localhost:8090";
		driver.navigate().to(URL);
		PO_View.checkElement(driver, "text", "90");
	}

	// 24. Sobre una búsqueda determinada (a elección del desarrollador), comprar
	// una oferta que deja un saldo 0 en el contador del comprador.
	// Comprobar que el contador se actualiza correctamente en la vista del
	// comprador.
	//NO FUNCIONA
	@Test
	public void PR24() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "pedro@email.es", "123456");
		// COmprobamos que entramos en la pagina privada de usuario
		PO_View.checkElement(driver, "text", "Gestión de ofertas");
		// Pinchamos la opcion de menu de usuario
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'ofertas-menu')]/a");
		elementos.get(0).click();
		// Buscamos la opcion de ver lista de usuario
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'oferta/search')]");
		elementos.get(0).click();
		// Comprobamos que tenga sus ofertas.
		WebElement buscar = driver.findElement(By.name("searchText"));
		buscar.click();
		buscar.clear();
		buscar.sendKeys("Abrigo");
		By boton = By.className("btn");
		driver.findElement(boton).click();

		// Comprar una sudadera
		elementos = PO_View.checkElement(driver, "free", "//button[contains(text(), 'Comprar')]");
		elementos.get(0).click();
		URL = "http://localhost:8090";
		driver.navigate().to(URL);
		PO_View.checkElement(driver, "text", "0");
	}
	
	
	// 25. Sobre una búsqueda determinada (a elección del desarrollador), intentar comprar una oferta
	// que esté por encima de saldo disponible del comprador. Y comprobar que se muestra el mensaje de
	// saldo no suficiente.
	//NO FUNCIONA
	@Test
	public void PR25() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "lucas@email.es", "123456");
		// COmprobamos que entramos en la pagina privada de usuario
		PO_View.checkElement(driver, "text", "Gestión de ofertas");
		// Pinchamos la opcion de menu de usuario
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'ofertas-menu')]/a");
		elementos.get(0).click();
		// Buscamos la opcion de ver lista de usuario
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'oferta/search')]");
		elementos.get(0).click();
		// Comprobamos que tenga sus ofertas.
		WebElement buscar = driver.findElement(By.name("searchText"));
		buscar.click();
		buscar.clear();
		buscar.sendKeys("Sombrero");
		By boton = By.className("btn");
		driver.findElement(boton).click();

		// Comprar un sombreo
		elementos = PO_View.checkElement(driver, "free", "//button[contains(text(), 'Comprar')]");
		//Como no tiene dinero se tendra que quedar el saldo en 100
		URL = "http://localhost:8090";
		driver.navigate().to(URL);
		PO_View.checkElement(driver, "text", "100");
	}

	// 26. Ir a la opción de ofertas compradas del usuario y mostrar la lista.
	// Comprobar que aparecen
	// las ofertas que deben aparecer
	@Test
	public void PR26() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "pedro@email.es", "123456");
		// COmprobamos que entramos en la pagina privada de usuario
		PO_View.checkElement(driver, "text", "Gestión de ofertas");
		// Pinchamos la opcion de menu de usuario
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'ofertas-menu')]/a");
		elementos.get(0).click();
		// Buscamos la opcion de ver lista de usuario
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'oferta/listCompras')]");
		elementos.get(0).click();
		// Comprobamos que tenemos el mensaje en ingles
		SeleniumUtils.esperarSegundos(driver, 2);
		PO_View.checkElement(driver, "text", "Sudadera");
	}

	// 27. Visualizar al menos cuatro páginas haciendo el cambio
	// español/inglés/español
	// (comprobando que algunas de las etiquetas cambian al idioma correspondiente).
	// Página principal/Opciones principales de usuario/Listado de usuarios /Vista
	// de alta de oferta.
	@Test
	public void PR27() {
		// primero comprobamos el mensaje de bienvenida
		PO_HomeView.checkChangeIdiom(driver, "btnSpanish", "btnEnglish", PO_Properties.getSPANISH(),
				PO_Properties.getENGLISH());
		// comprobamos las opciones del usuario
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "admin@email.es", "123456");
		// Comprobamos las opciones de menu
		PO_NavView.checkChangeIdiom(driver, "btnSpanish", "btnEnglish", PO_Properties.getSPANISH(),
				PO_Properties.getENGLISH());
		// entramos en el listado de usuarios
		// Pinchamos la opcion de menu de usuario
		List<WebElement> elementoss = PO_View.checkElement(driver, "free", "//li[contains(@id, 'users-menu')]/a");
		elementoss.get(0).click();
		// Buscamos la opcion de ver lista de usuario
		elementoss = PO_View.checkElement(driver, "free", "//a[contains(@href, 'user/list')]");
		elementoss.get(0).click();
		// cambiamos de idioma
		PO_PrivateView.checkChangeIdiom(driver, "btnSpanish", "btnEnglish", PO_Properties.getSPANISH(),
				PO_Properties.getENGLISH());
		// por ultimo comprobamos la vista de alta de oferta
		// Pinchamos la opcion de menu de usuario
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'ofertas-menu')]/a");
		elementos.get(0).click();
		// Buscamos la opcion de ver lista de usuario
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'oferta/add')]");
		elementos.get(0).click();

	}

	// 28. Intentar acceder sin estar autenticado a la opción de listado de usuarios
	// del administrador. Se
	// deberá volver al formulario de login.
	@Test
	public void PR28() {
		URL = "http://localhost:8090/user/list";

		driver.navigate().to(URL);
		PO_View.checkElement(driver, "text", "Identifícate");
	}

	// 29. Intentar acceder sin estar autenticado a la opción de listado de ofertas
	// propias de un usuario
	// estándar. Se deberá volver al formulario de login.
	@Test
	public void PR29() {
		URL = "http://localhost:8090/oferta/list";

		driver.navigate().to(URL);
		PO_View.checkElement(driver, "text", "Identifícate");
	}

	// 30. Estando autenticado como usuario estándar intentar acceder a la opción de
	// listado de
	// usuarios del administrador. Se deberá indicar un mensaje de acción prohibida.
	@Test
	public void PR30() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "pedro@email.es", "123456");
		// COmprobamos que entramos en la pagina privada de usuario
		PO_View.checkElement(driver, "text", "Gestión de ofertas");

		URL = "http://localhost:8090/user/list";

		driver.navigate().to(URL);

		SeleniumUtils.textoPresentePagina(driver, "HTTP Status 403 – Forbidden");
	}
	
	
	// 13. Ir a la lista de usuarios, borrar el primer usuario de la lista,
	// comprobar que la lista se actualiza
	// y que el usuario desaparece.
	@Test
	public void PR31() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "admin@email.es", "123456");
		// COmprobamos que entramos en la pagina privada de usuario
		PO_View.checkElement(driver, "text", "Gestión de ofertas");
		// Pinchamos la opcion de menu de usuario
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'users-menu')]/a");
		elementos.get(0).click();
		// Buscamos la opcion de ver lista de usuario
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'user/list')]");
		elementos.get(0).click();
		// Comprobamos que aparecen los usuarios
		List<WebElement> check =PO_View.checkElement(driver, "class", "checkbox");
		int numCheckBox = check.size();
		assertEquals(7, numCheckBox);
		check.get(0).click();
		PO_View.checkElement(driver, "id", "deleteButtonUsuarios").get(0).click();
		check =PO_View.checkElement(driver, "class", "checkbox");
		int num2CheckBox= check.size();
		assertEquals(num2CheckBox,numCheckBox-1);
	}

}