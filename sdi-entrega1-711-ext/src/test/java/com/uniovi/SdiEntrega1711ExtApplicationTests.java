package com.uniovi;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

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
	// static String Geckdriver024 =
	// "C:\\Users\\santi\\Desktop\\Informatica\\Tercero\\SegundoCuatri\\SDI\\PL-SDI-Sesión5-material\\geckodriver024win64.exe";
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
	@Test
	public void PR04() {
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "lucas@email.es", "Josefo", "Perez", "77777", "77777");
		// Comprobamos el error de email repetido.
		PO_RegisterView.checkKey(driver, "Error.signup.email.duplicate", PO_Properties.getSPANISH());

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
		// Comprobamos que entramos en la pagina privada de usuario
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
		PO_View.checkElement(driver, "text", "Gestión de usuarios");
		// Ahora nos desconectamos
		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
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
	@Test
	public void PR12() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "admin@email.es", "123456");
		// COmprobamos que entramos en la pagina privada de usuario
		PO_View.checkElement(driver, "text", "Gestión de usuarios");
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
		assertTrue(numElementos.size() == 6);
	}

	// 13. Ir a la lista de usuarios, borrar el primer usuario de la lista,
	// comprobar que la lista se actualiza
	// y que el usuario desaparece.
	@Test
	public void PR13() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "admin@email.es", "123456");
		// COmprobamos que entramos en la pagina privada de usuario
		PO_View.checkElement(driver, "text", "Gestión de usuarios");
		// Pinchamos la opcion de menu de usuario
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'users-menu')]/a");
		elementos.get(0).click();
		// Buscamos la opcion de ver lista de usuario
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'user/list')]");
		elementos.get(0).click();
		// Comprobamos que aparecen los usuarios
		List<WebElement> check = PO_View.checkElement(driver, "class", "checkbox");
		int numCheckBox = check.size();
		assertEquals(6, numCheckBox);
		check.get(0).click();
		PO_View.checkElement(driver, "id", "deleteButtonUsuarios").get(0).click();
		check = PO_View.checkElement(driver, "class", "checkbox");
		int num2CheckBox = check.size();
		assertEquals(num2CheckBox, numCheckBox - 1);
	}

	// 14. Ir a la lista de usuarios, borrar el último usuario de la lista,
	// comprobar que la lista se actualiza
	// y que el usuario desaparece.
	@Test
	public void PR14() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "admin@email.es", "123456");
		// COmprobamos que entramos en la pagina privada de usuario
		PO_View.checkElement(driver, "text", "Gestión de usuarios");
		// Pinchamos la opcion de menu de usuario
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'users-menu')]/a");
		elementos.get(0).click();
		// Buscamos la opcion de ver lista de usuario
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'user/list')]");
		elementos.get(0).click();
		// Comprobamos que aparecen los usuarios
		List<WebElement> check = PO_View.checkElement(driver, "class", "checkbox");
		int numCheckBox = check.size();
		assertEquals(5, numCheckBox);
		check.get(numCheckBox - 1).click();
		PO_View.checkElement(driver, "id", "deleteButtonUsuarios").get(0).click();
		check = PO_View.checkElement(driver, "class", "checkbox");
		int num2CheckBox = check.size();
		assertEquals(num2CheckBox, numCheckBox - 1);
	}

	// 15. Ir a la lista de usuarios, borrar 3 usuarios, comprobar que la lista se
	// actualiza y que los
	// usuarios desaparecen.
	@Test
	public void PR15() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "admin@email.es", "123456");
		// COmprobamos que entramos en la pagina privada de usuario
		PO_View.checkElement(driver, "text", "Gestión de usuarios");
		// Pinchamos la opcion de menu de usuario
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'users-menu')]/a");
		elementos.get(0).click();
		// Buscamos la opcion de ver lista de usuario
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'user/list')]");
		elementos.get(0).click();
		// Comprobamos que aparecen los usuarios
		List<WebElement> check = PO_View.checkElement(driver, "class", "checkbox");
		int numCheckBox = check.size();
		assertEquals(4, numCheckBox);
		check.get(0).click();
		check.get(1).click();
		check.get(2).click();
		PO_View.checkElement(driver, "id", "deleteButtonUsuarios").get(0).click();
		SeleniumUtils.esperarSegundos(driver, 2);
		check = PO_View.checkElement(driver, "class", "checkbox");
		int num2CheckBox = check.size();
		assertEquals(num2CheckBox, 3);
	}

	// 16.Ir al formulario de alta de oferta, rellenarla con datos válidos y pulsar
	// el botón Submit.
	// Comprobar que la oferta sale en el listado de ofertas de dicho usuario
	@Test
	public void PR16() {
		// Primero volvemos a añadir el usuario que usaremos en adelante
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "pedro@email.es", "Pedro", "Garcia", "123456", "123456");
		// COmprobamos que entramos en la pagina privada de usuario
		PO_View.checkElement(driver, "text", "Gestión de ofertas");
		// Pinchamos la opcion de menu de usuario
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'ofertas-menu')]/a");
		elementos.get(0).click();
		// Buscamos la opcion de agregar oferta
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'oferta/add')]");
		elementos.get(0).click();
		// Rellenamos el formulario para subir la oferta
		PO_AgregarOfertaView.fillForm(driver, "Camiseta azul", "Talla XL", "10");
		PO_View.checkElement(driver, "text", "Camiseta azul");
		List<WebElement> elementos1 = PO_View.checkElement(driver, "free", "//li[contains(@id, 'ofertas-menu')]/a");
		elementos1.get(0).click();
		// Buscamos la opcion de agregar oferta
		elementos1 = PO_View.checkElement(driver, "free", "//a[contains(@href, 'oferta/add')]");
		elementos1.get(0).click();
		// Rellenamos el formulario para subir la oferta
		PO_AgregarOfertaView.fillForm(driver, "Pantalon azul", "Talla 40", "10");
		PO_View.checkElement(driver, "text", "Pantalon azul");
	}

	// 17. Ir al formulario de alta de oferta, rellenarla con datos inválidos (campo
	// título vacío) y pulsar
	// el botón Submit. Comprobar que se muestra el mensaje de campo obligatorio.
	@Test
	public void PR17() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "pedro@email.es", "123456");
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
	@Test
	public void PR18() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "pedro@email.es", "123456");
		// Pinchamos la opcion de menu de usuario
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'ofertas-menu')]/a");
		elementos.get(0).click();
		// Buscamos la opcion de ver lista de usuario
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'oferta/list')]");
		elementos.get(0).click();

		PO_View.checkElement(driver, "text", "Camiseta azul");
	}

	// 19. Ir a la lista de ofertas, borrar la primera oferta de la lista, comprobar
	// que la lista se actualiza y
	// que la oferta desaparece.
	@Test
	public void PR19() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "pedro@email.es", "123456");
		// Pinchamos la opcion de menu de usuario
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'ofertas-menu')]/a");
		elementos.get(0).click();
		// Buscamos la opcion de ver lista de usuario
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'oferta/list')]");
		elementos.get(0).click();
		// Comprobamos que tenga sus ofertas.
		PO_View.checkElement(driver, "text", "Camiseta azul");
		// Eliminamos la primera
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'oferta/delete/24')]");
		elementos.get(0).click();
		// Comprobamos que no se encuentre
		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "Camiseta azul", PO_View.getTimeout());
	}

	// 20. Ir a la lista de ofertas, borrar la última oferta de la lista,
	// comprobar que la lista se actualiza y que la oferta desaparece.
	@Test
	public void PR20() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "pedro@email.es", "123456");
		// Comprobamos que entramos en la pagina privada de usuario
		PO_View.checkElement(driver, "text", "Gestión de ofertas");
		// Pinchamos la opcion de menu de usuario
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'ofertas-menu')]/a");
		elementos.get(0).click();
		// Buscamos la opcion de ver lista de usuario
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'oferta/list')]");
		elementos.get(0).click();
		// Comprobamos que tenga sus ofertas.
		PO_View.checkElement(driver, "text", "Pantalon azul");
		// Eliminamos la primera
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'oferta/delete/25')]");
		elementos.get(0).click();
		// Comprobamos que no se encuentre
		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "Pantalon azul", PO_View.getTimeout());
	}

	// 21. Hacer una búsqueda con el campo vacío y comprobar que se muestra la
	// página que corresponde con el listado de las ofertas existentes en el sistema
	@Test
	public void PR21() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
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

	// 22. Hacer una búsqueda escribiendo en el campo un texto que no exista y
	// comprobar
	// que se muestra la página que corresponde, con la lista de ofertas vacía.
	@Test
	public void PR22() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
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
	// una oferta que deja un saldo positivo en el contador del comprador. Comprobar
	// que el contador se
	// actualiza correctamente en la vista del comprador.
	@Test
	public void PR23() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
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
		buscar.sendKeys("Zapatos adidas");
		By boton = By.className("btn");
		driver.findElement(boton).click();
		// Comprar una sudadera
		elementos = PO_View.checkElement(driver, "free", "//button[contains(text(), 'Comprar')]");
		elementos.get(0).click();
		URL = "http://localhost:8090/home";
		driver.navigate().to(URL);
		PO_View.checkElement(driver, "text", "90");
	}

	// 24. Sobre una búsqueda determinada (a elección del desarrollador), comprar
	// una oferta que deja un saldo 0 en el contador del comprador.
	// Comprobar que el contador se actualiza correctamente en la vista del
	// comprador.
	@Test
	public void PR24() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "pedro@email.es", "123456");
		// COmprobamos que entramos en la pagina privada de usuario
		PO_View.checkElement(driver, "text", "Gestión de ofertas");
		// Pinchamos la opcion de menu de usuario
		// añadimos la siguiente oferta para la siguiente prueba
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'ofertas-menu')]/a");
		elementos.get(0).click();
		// Buscamos la opcion de agregar oferta
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'oferta/add')]");
		elementos.get(0).click();
		// Rellenamos el formulario para subir la oferta
		PO_AgregarOfertaView.fillForm(driver, "Camiseta Blueberry", "Talla XXL", "110");
		// Pinchamos la opcion de menu de usuario
		List<WebElement> elementos1 = PO_View.checkElement(driver, "free", "//li[contains(@id, 'ofertas-menu')]/a");
		elementos1.get(0).click();
		// Buscamos la opcion de ver lista de usuario
		elementos1 = PO_View.checkElement(driver, "free", "//a[contains(@href, 'oferta/search')]");
		elementos1.get(0).click();
		// Comprobamos que tenga sus ofertas.
		WebElement buscar = driver.findElement(By.name("searchText"));
		buscar.click();
		buscar.clear();
		buscar.sendKeys("Zapatos");
		By boton = By.className("btn");
		driver.findElement(boton).click();
		// Comprar unos zapatos
		elementos = PO_View.checkElement(driver, "free", "//button[contains(text(), 'Comprar')]");
		elementos.get(0).click();
		URL = "http://localhost:8090/home";
		driver.navigate().to(URL);
		PO_View.checkElement(driver, "text", "0");
	}

	// 25. Sobre una búsqueda determinada (a elección del desarrollador), intentar
	// comprar una oferta que esté por encima de saldo disponible del comprador. Y
	// comprobar que se muestra el mensaje de saldo no suficiente.
	@Test
	public void PR25() {
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "lucas2@email.es", "Lucas", "Garcia", "123456", "123456");
		// COmprobamos que entramos en la pagina privada de usuario
		SeleniumUtils.esperarSegundos(driver, 2);
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
		buscar.sendKeys("Camiseta Blueberry");
		By boton = By.className("btn");
		driver.findElement(boton).click();
		PO_View.checkElement(driver, "text", "Sin saldo");
		// Como no tiene dinero se tendra que quedar el saldo en 100
		URL = "http://localhost:8090/home";
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
		PO_LoginView.fillForm(driver, "pedro@email.es", "123456");
		// COmprobamos que entramos en la pagina privada de usuario
		PO_View.checkElement(driver, "text", "Gestión de ofertas");
		// Pinchamos la opcion de menu de usuario
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'ofertas-menu')]/a");
		elementos.get(0).click();
		// Buscamos la opcion de ver lista de usuario
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'oferta/listCompras')]");
		SeleniumUtils.esperarSegundos(driver, 2);
		elementos.get(0).click();
		PO_View.checkElement(driver, "text", "Zapatos adidas");
		PO_View.checkElement(driver, "text", "Zapatos");
	}

	// 27. Visualizar al menos cuatro páginas haciendo el cambio
	// español/inglés/español
	// (comprobando que algunas de las etiquetas cambian al idioma correspondiente).
	// Página principal/Opciones principales de usuario/Listado de usuarios /Vista
	// de alta de oferta.
	@Test
	public void PR27() {
		URL = "http://localhost:8090/";
		driver.navigate().to(URL);
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
		// nos desconectamos para entrar como usuario normal
		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
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
		PO_LoginView.fillForm(driver, "pedro@email.es", "123456");
		// Comprobamos que entramos en la pagina privada de usuario
		PO_View.checkElement(driver, "text", "Gestión de ofertas");

		URL = "http://localhost:8090/user/list";

		driver.navigate().to(URL);

		SeleniumUtils.textoPresentePagina(driver, "HTTP Status 403 – Forbidden");

	}

	// 36. Al crear una oferta marcar dicha oferta como destacada y a continuación
	// comprobar: i) que aparece en el listado de ofertas destacadas para los
	// usuarios y que el saldo del usuario se actualiza adecuadamente en la vista
	// del ofertante (-20).
	@Test
	public void PR36() {
		URL = "http://localhost:8090/home";
		driver.navigate().to(URL);
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "lucas2@email.es", "123456");
		// Comprobamos que entramos en la pagina privada de usuario
		PO_View.checkElement(driver, "text", "Gestión de ofertas");
		// Pinchamos la opcion de menu de usuario
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'ofertas-menu')]/a");
		elementos.get(0).click();
		// Buscamos la opcion de ver lista de usuario
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'oferta/add')]");
		elementos.get(0).click();
		PO_AgregarOfertaView.fillForm(driver, "Nueva oferta destacada1", "oferta destacada de prueba", "20", true);
		// comprobamos que el dinero se haya actualizado, como el usuario tenia 100,
		// ahora tendrá 80
		driver.navigate().to("http://localhost:8090/home");
		PO_HomeView.checkElement(driver, "text", "80");
		List<WebElement> elementos1 = PO_View.checkElement(driver, "free", "//li[contains(@id, 'ofertas-menu')]/a");
		elementos1.get(0).click();
		// Buscamos la opcion de ver lista de usuario
		elementos1 = PO_View.checkElement(driver, "free", "//a[contains(@href, 'oferta/add')]");
		elementos1.get(0).click();
		PO_AgregarOfertaView.fillForm(driver, "Nueva oferta destacada2", "oferta destacada de prueba", "20", true);
		// comprobamos que el dinero se haya actualizado, como el usuario tenia 80,
		// ahora tendrá 60
		driver.navigate().to("http://localhost:8090/home");
		PO_HomeView.checkElement(driver, "text", "60");
		List<WebElement> elementos2 = PO_View.checkElement(driver, "free", "//li[contains(@id, 'ofertas-menu')]/a");
		elementos2.get(0).click();
		// Buscamos la opcion de ver lista de usuario
		elementos2 = PO_View.checkElement(driver, "free", "//a[contains(@href, 'oferta/add')]");
		elementos2.get(0).click();
		PO_AgregarOfertaView.fillForm(driver, "Nueva oferta destacada3", "oferta destacada de prueba", "20", true);
		// comprobamos que el dinero se haya actualizado, como el usuario tenia 60,
		// ahora tendrá 40
		driver.navigate().to("http://localhost:8090/home");
		PO_HomeView.checkElement(driver, "text", "40");
		List<WebElement> elementos3 = PO_View.checkElement(driver, "free", "//li[contains(@id, 'ofertas-menu')]/a");
		elementos3.get(0).click();
		// Buscamos la opcion de ver lista de usuario
		elementos3 = PO_View.checkElement(driver, "free", "//a[contains(@href, 'oferta/add')]");
		elementos3.get(0).click();
		PO_AgregarOfertaView.fillForm(driver, "Nueva oferta destacada4", "oferta destacada de prueba", "20", true);
		// comprobamos que el dinero se haya actualizado, como el usuario tenia 40,
		// ahora tendrá 20
		driver.navigate().to("http://localhost:8090/home");
		PO_HomeView.checkElement(driver, "text", "20");
		// nos desconectamos para comprbar que desde otro usuario podemos ver la oferta
		// destacada
		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
		// entramos en sesion
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "pedro@email.es", "123456");
		// Comprobamos que entramos en la pagina privada de usuario
		PO_View.checkElement(driver, "text", "Gestión de ofertas");
		// comprobamos que aparece la oferta destacada que acabamos de añadir
		PO_HomeView.checkElement(driver, "text", "Nueva oferta destacada1");

	}

	// 37. Sobre el listado de ofertas de un usuario con menos de 20 euros de saldo,
	// pinchar en el enlace Destacada y a continuación comprobar: que aparece en el
	// listado de ofertas destacadas para los usuarios y que el saldo del usuario se
	// actualiza adecuadamente en la vista del ofertante (-20).
	@Test
	public void PR37() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "lucas2@email.es", "123456");
		// Comprobamos que entramos en la pagina privada de usuario
		PO_View.checkElement(driver, "text", "Gestión de ofertas");
		// Pinchamos la opcion de menu de usuario
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'ofertas-menu')]/a");
		elementos.get(0).click();
		// Buscamos la opcion de ver lista de usuario
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'oferta/add')]");
		elementos.get(0).click();
		PO_AgregarOfertaView.fillForm(driver, "Nueva oferta normal", "oferta destacada de prueba", "20");
		//vamos a la lista fe ofertas propias
		driver.navigate().to("http://localhost:8090/oferta/list");
		elementos = PO_View.checkElement(driver, "free", "//button[contains(text(), 'Poner como destacada')]");
		elementos.get(0).click();
		driver.navigate().to("http://localhost:8090/home");
		PO_HomeView.checkElement(driver, "text", "0");
	}

	// 38. Sobre el listado de ofertas de un usuario con menos de 20 euros de saldo,
	// pinchar en el enlace Destacada y a continuación comprobar que se muestra el
	// mensaje de saldo no suficiente.
	@Test
	public void PR38() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "lucas2@email.es", "123456");
		// Comprobamos que entramos en la pagina privada de usuario
		PO_View.checkElement(driver, "text", "Gestión de ofertas");
		// Pinchamos la opcion de menu de usuario
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'ofertas-menu')]/a");
		elementos.get(0).click();
		// Buscamos la opcion de ver lista de usuario
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'oferta/add')]");
		elementos.get(0).click();
		PO_AgregarOfertaView.fillForm(driver, "Nueva oferta normal2", "oferta destacada de prueba", "20");
		//vamos a la lista fe ofertas propias
		driver.navigate().to("http://localhost:8090/oferta/list?page=1");
		elementos = PO_View.checkElement(driver, "free", "//button[contains(text(), 'Poner como destacada')]");
		elementos.get(0).click();
		PO_View.checkElement(driver, "text", "No hay suficiente saldo");
		driver.navigate().to("http://localhost:8090/home");
		PO_HomeView.checkElement(driver, "text", "0");
	}

}