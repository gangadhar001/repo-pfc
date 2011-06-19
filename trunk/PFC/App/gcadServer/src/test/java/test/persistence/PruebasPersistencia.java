package test.persistence;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import exceptions.IncorrectAddressException;
import exceptions.IncorrectEmployeeException;

import persistence.DAOAddress;
import persistence.DAOCompany;
import persistence.DAOProject;
import persistence.DAOUser;

import model.business.knowledge.Address;
import model.business.knowledge.Answer;
import model.business.knowledge.Categories;
import model.business.knowledge.ChiefProject;
import model.business.knowledge.Company;
import model.business.knowledge.Employee;
import model.business.knowledge.Notification;
import model.business.knowledge.Project;
import model.business.knowledge.Proposal;
import model.business.knowledge.Topic;
import model.business.knowledge.User;

import test.communication.PruebasBase;

/**
 * Pruebas de las clases de persistencia.
 */
public class PruebasPersistencia extends PruebasBase {
	
	private Address address;
	private Employee employee;
	private Company company;
	private ChiefProject chief;
	private Project project, project2;
	private Proposal pro;
	private Topic topic;
	private Answer ans;
	private Set<User> users;
	private Notification not;
	private HashSet<Project> projects;
	private User emp;

	@SuppressWarnings("deprecation")
	protected void setUp() {
		try {
			// Preparamos la base de datos
			super.setUp();
			// Creamos objetos de prueba
			address = new Address("street", "city", "country", "zip");
			company = new Company("456as", "company", "information", address);
			employee = new Employee("12345678L", "emp1", "emp1", "User1", "emp1", "", "", 2, company);
			chief = new ChiefProject("65413987L", "emp3", "emp3", "User3", "chief1", "", "", 12, company);
			project = new Project("project", "desc1", new Date(), new Date(), 203.36, 458, "bank", "java", 557);
			project2 = new Project("project2", "desc2", new Date(), new Date(), 53683.36, 45128, "bank", "C#", 5571);
			projects = new HashSet<Project>();
			pro = new Proposal("pro", "desc", new Date(), Categories.Analysis);
			ans = new Answer("ans", "desc", new Date(), "Pro");
			topic = new Topic("pro", "desc", new Date());
			users = new HashSet<User>();
			users.add(chief);
			users.add(employee);
			not = new Notification(topic, "Unread", project, "subject", users);
		} catch(Exception e) {
			fail(e.toString());
		}
	}
	
	protected void tearDown() {
		try {
			// Cerramos la base de datos
			super.tearDown();
		} catch(Exception e) {
			fail(e.toString());
		}
	}
	
	/** Pruebas de la tabla de usuarios */
	public void testUsers() {
		List<User> users;
		
		try {
			// Intentamos buscar un usuario inexistente
			DAOUser.queryUser("log", "log");
			fail("Se esperaba una excepción IncorrectEmployeeException");
		} catch(IncorrectEmployeeException e) {
		} catch(Exception e) {
			fail("Se esperaba una excepción IncorrectEmployeeException");
		}
		
		try {
			// Añadimos los usuarios
			DAOAddress.insert(address);
			DAOCompany.insert(company);
			DAOUser.insert(employee);
			DAOUser.insert(chief);
		} catch(Exception e) {
			fail(e.toString());
		}
		
		try {
			// Modificamos un beneficiario
			employee.setSurname("V. L.");
			employee.setEmail("nuevocorreo@terra.com");
			employee.setName("John");
			employee.setTelephone("612312333");
			employee.setSeniority(12);
			DAOUser.update(employee);
			// Comprobamos si los cambios han tenido efecto
			emp = DAOUser.queryUser(employee.getLogin(), employee.getPassword());
			assertEquals(employee, emp);
		} catch(Exception e) {
			fail(e.toString());
		}
		
		try {
			// Obtenemos la lista de usuarios
			users = DAOUser.getUsers();
			assertTrue(users.size() == 2);
			assertTrue((users.get(0).equals(employee) && users.get(1).equals(chief)) 
			           || (users.get(1).equals(employee) && users.get(0).equals(chief))); 
		} catch(Exception e) {
			fail(e.toString());
		}
		
		try {
			// Añadimos el usuario a proyectos
			DAOProject.insert(project);
			DAOProject.insert(project2);
			projects.add(project);
			employee.setProjects(projects);
			Set<Project> pr = employee.getProjects();
			pr.add(project2);
			employee.setProjects(pr);
			DAOUser.update(employee);
			emp = DAOUser.queryUser(employee.getLogin(), employee.getPassword());
			assertTrue(pr.size() == 2);
			assertEquals(employee.getProjects().size(), pr.size());
		} catch(Exception e) {
			fail(e.toString());
		}
		
		try {
			// Obtenemos la lista de usuarios de un proyecto
			users = DAOUser.getUsersProject(project);
			assertTrue(users.size() == 1);
			assertTrue(users.get(0).equals(employee)); 
		} catch(Exception e) {
			fail(e.toString());
		}		
		
		try {
			// Eliminamos un usuario
			DAOUser.delete(employee);
			// Comprobamos si los cambios han tenido efecto
			DAOUser.queryUser(employee.getLogin(), employee.getPassword());
			fail("Se esperaba una excepción IncorrectEmployeeException");
		} catch(IncorrectEmployeeException e) {
		} catch(Exception e) {
			fail("Se esperaba una excepción IncorrectEmployeeException");
		}
		
		try {
			// Comprobamos que los usuarios no borrados siguen existiendo
			emp = DAOUser.queryUser(chief.getLogin(), chief.getPassword());
			assertEquals(chief, emp);			
		} catch(Exception e) {
			fail(e.toString());
		}		

		try {
			// Intentamos insertar un usuario con un NIF existente
			chief.setNif(employee.getNif());
			DAOUser.insert(chief);
			fail("Se esperaba una excepción SQLException");
		} catch(SQLException e) {
		} catch(Exception e) {
			fail("Se esperaba una excepción SQLException");
		}		
	}

//
//	/** Pruebas de la tabla de citas */
	public void testCitas() {
		try {
			// Intentamos buscar una direccion inexistente
			DAOAddress.queryAddress(0);
			fail("Se esperaba una excepción IncorrectAddressException");
		} catch(IncorrectAddressException e) {
		} catch(Exception e) {
			fail("Se esperaba una excepción IncorrectAddressException");
		}
		
		try {
			// Añadimos la direccion
			DAOAddress.insert(address);
			DAOCompany.insert(company);
		} catch(Exception e) {
			fail(e.toString());
		}
		
		try {
			// Modificamos la direccion
			address.setCity("L.A");
			address.setZip("13170");
			address.setCountry("Spain");
			address.setStreet("street");
			DAOAddress.update(address);
			// Comprobamos si los cambios han tenido efecto
			Address add = DAOAddress.queryAddress(address.getId());
			assertEquals(address, add);
		} catch(Exception e) {
			fail(e.toString());
		}
		
		try {
			// Eliminamos una direccion
			DAOAddress.delete(address);
			// Comprobamos si los cambios han tenido efecto
			DAOAddress.queryAddress(address.getId());
			fail("Se esperaba una excepción IncorrectAddressException");
			Company comp = DAOCompany.queryCompany(company.getId());
			assertTrue(comp.getAddress() == null);
		} catch(IncorrectAddressException e) {
		} catch(Exception e) {
			fail("Se esperaba una excepción IncorrectAddressException");
		}			
	}
//		
//	/** Pruebas de la tabla de entradas del log */
//	public void testEntradasLog() {
//		Vector<EntradaLog> log;
//		
//		try {
//			// Consultamos las entradas sin haber ninguna en
//			// el log para ver si se devuelve una lista vacía
//			log = FPEntradaLog.consultarLog();
//			assertTrue(log != null && log.size() == 0);
//		} catch(Exception e) {
//			fail(e.toString());
//		}
//		
//		try {
//			// Insertamos varios usuarios
//			FPCentroSalud.insertar(centro1);
//			FPUsuario.insertar(medico1);
//			FPUsuario.insertar(citador1);
//			FPUsuario.insertar(administrador1);
//			// Insertamos nuevas entradas válidas
//			// (y repetidas, que se permite)
//			FPEntradaLog.insertar(entrada1);
//			FPEntradaLog.insertar(entrada2);
//			FPEntradaLog.insertar((EntradaLog)entrada1.clone());
//		} catch(Exception e) {
//			fail(e.toString());
//		}
//		
//		try {
//			// Intentamos insertar una entrada con un tipo
//			// de mensaje o acción no permitido
//			FPEntradaLog.insertar(entrada6);
//			fail("Se esperaba una excepción SQLException");
//		} catch(SQLException e) {
//		} catch(Exception e) {
//			fail("Se esperaba una excepción SQLException");
//		}
//
//		try {
//			// Comprobamos que las entradas se hayan añadido bien
//			log = FPEntradaLog.consultarLog();
//			assertTrue(log.size() == 3);
//			assertTrue((log.get(0).equals(entrada1) && log.get(1).equals(entrada2)
//			           || (log.get(0).equals(entrada2) && log.get(1).equals(entrada1))));
//		} catch(Exception e) {
//			fail(e.toString());
//		}
//		
//		try {
//			// Insertamos entradas con el resto de tipos de mensaje
//			FPEntradaLog.insertar(entrada3);
//			FPEntradaLog.insertar(entrada4);
//			FPEntradaLog.insertar(entrada5);
//		} catch(Exception e) {
//			fail(e.toString());
//		}
//	}
//	
//	/** Pruebas de la tabla de sustituciones */
//	public void testSustituciones() {
//		Vector<Sustitucion> sustituciones;
//		
//		try {
//			// Añadimos varios médicos a la base de datos
//			FPCentroSalud.insertar(centro1);
//			FPUsuario.insertar(medico1);
//			FPUsuario.insertar(medico2);
//			// Intentamos consultar las sustituciones antes de añadirlas
//			// para comprobar que se devuelve una lista vacía
//			sustituciones = FPSustitucion.consultarPorSustituido(medico1.getNif());
//			assertTrue(sustituciones.size() == 0);
//			sustituciones = FPSustitucion.consultarPorSustituto(medico2.getNif());
//			assertTrue(sustituciones.size() == 0);
//		} catch(Exception e) {
//			fail(e.toString());
//		}
//		
//		try {
//			// Intentamos leer las sustituciones de un médico que no existe
//			FPSustitucion.consultarPorSustituido("01233210N");
//			fail("Se esperaba una excepción UsuarioIncorrectoException");
//		} catch(UsuarioIncorrectoException e) {
//		} catch(Exception e) {
//			fail("Se esperaba una excepción UsuarioIncorrectoException");
//		}
//		
//		try {
//			// Intentamos leer las sustituciones hechas por un médico que no existe
//			FPSustitucion.consultarPorSustituto("01233210N");
//			fail("Se esperaba una excepción UsuarioIncorrectoException");
//		} catch(UsuarioIncorrectoException e) {
//		} catch(Exception e) {
//			fail("Se esperaba una excepción UsuarioIncorrectoException");
//		}
//		
//		try {
//			// Insertamos varias sustituciones correctas
//			FPSustitucion.insertar(sustitucion1);
//			FPSustitucion.insertar(sustitucion2);
//			// Recuperamos las sustituciones almacenadas
//			sustituciones = FPSustitucion.consultarPorSustituido(medico1.getNif());
//			assertTrue((sustituciones.get(0).equals(sustitucion1) && sustituciones.get(1).equals(sustitucion2)
//			           || (sustituciones.get(0).equals(sustitucion2) && sustituciones.get(1).equals(sustitucion1))));
//			sustituciones = FPSustitucion.consultarPorSustituto(medico2.getNif());
//			assertTrue((sustituciones.get(0).equals(sustitucion1) && sustituciones.get(1).equals(sustitucion2)
//			           || (sustituciones.get(0).equals(sustitucion2) && sustituciones.get(1).equals(sustitucion1))));
//		} catch(Exception e) {
//			fail(e.toString());
//		}
//		
//		try {
//			// Intentamos consultar las sustituciones de un
//			// usuario que existe pero no es médico
//			FPUsuario.insertar(administrador1);
//			FPSustitucion.consultarPorSustituido(administrador1.getNif());
//			fail("Se esperaba una excepción UsuarioIncorrectoException");
//		} catch(UsuarioIncorrectoException e) {
//		} catch(Exception e) {
//			fail("Se esperaba una excepción UsuarioIncorrectoException");
//		}
//		
//		try {
//			// Intentamos consultar las sustituciones hechas
//			// por un usuario que existe pero no es médico
//			FPUsuario.insertar(citador1);
//			FPSustitucion.consultarPorSustituto(citador1.getNif());
//			fail("Se esperaba una excepción UsuarioIncorrectoException");
//		} catch(UsuarioIncorrectoException e) {
//		} catch(Exception e) {
//			fail("Se esperaba una excepción UsuarioIncorrectoException");
//		}
//	}
//	
//	/** Pruebas de la tabla de usuarios */
//	public void testUsuarios() {
//		Vector<String> medicos;
//		Usuario usuario, usuarioAux;
//		
//		try {
//			// Intentamos buscar un usuario sin haber creado ninguno
//			usuario = FPUsuario.consultar("1234567");
//			fail("Se esperaba una excepción UsuarioIncorrectoException");
//		} catch(UsuarioIncorrectoException e) {
//		} catch(Exception e) {
//			fail("Se esperaba una excepción UsuarioIncorrectoException");
//		}
//		
//		try {
//			// Intentamos buscar un usuario sin haber creado ninguno
//			usuario = FPUsuario.consultarPorLogin("login");
//			fail("Se esperaba una excepción UsuarioIncorrectoException");
//		} catch(UsuarioIncorrectoException e) {
//		} catch(Exception e) {
//			fail("Se esperaba una excepción UsuarioIncorrectoException");
//		}
//		
//		try {
//			// Intentamos buscar un usuario sin haber creado ninguno
//			usuario = FPUsuario.consultar("login", "pass");
//			fail("Se esperaba una excepción UsuarioIncorrectoException");
//		} catch(UsuarioIncorrectoException e) {
//		} catch(Exception e) {
//			fail("Se esperaba una excepción UsuarioIncorrectoException");
//		}
//		
//		try {
//			// Intentamos obtener una lista con los médicos especialistas
//			// antes de añadirlos para ver que se devuelve una lista vacía
//			medicos = FPUsuario.consultarMedicos(new Especialista(""));
//			assertTrue(medicos.size() == 0);
//		} catch(Exception e) {
//			fail(e.toString());
//		}
//
//		try {
//			// Añadimos los centros de salud asociados a los usuarios
//			FPCentroSalud.insertar(centro1);
//			FPCentroSalud.insertar(centro2);
//			FPCentroSalud.insertar(centro3);
//			// Insertamos varios usuarios correctos de todos los tipos
//			medico1.getCalendario().add(periodo1);
//			medico2.getCalendario().add(periodo2);
//			FPUsuario.insertar(medico1);
//			FPUsuario.insertar(medico2);
//			FPUsuario.insertar(medico3);
//			FPUsuario.insertar(medico4);
//			FPUsuario.insertar(citador1);
//			FPUsuario.insertar(administrador1);
//		} catch(Exception e) {
//			fail(e.toString());
//		}
//		
//		try {
//			// Intentamos insertar un usuario con un NIF existente
//			usuarioAux = (Usuario)medico1.clone();
//			FPUsuario.insertar(usuarioAux);
//			fail("Se esperaba una excepción SQLException");
//		} catch(SQLException e) {
//		} catch(Exception e) {
//			fail("Se esperaba una excepción SQLException");
//		}
//
//		try {
//			// Intentamos insertar un usuario con un login existente
//			FPUsuario.insertar(citador2);
//			fail("Se esperaba una excepción SQLException");
//		} catch(SQLException e) {
//		} catch(Exception e) {
//			fail("Se esperaba una excepción SQLException");
//		}
//		
//		try {
//			// Recuperamos varios usuarios insertados de las tres formas posibles
//			usuario = FPUsuario.consultar(medico1.getNif());
//			assertEquals(medico1, usuario);
//			usuario = FPUsuario.consultar(citador1.getNif());
//			assertEquals(citador1, usuario);
//			usuario = FPUsuario.consultar(medico1.getLogin(), medico1.getPassword());
//			assertEquals(medico1, usuario);
//			usuario = FPUsuario.consultar(citador1.getLogin(), citador1.getPassword());
//			assertEquals(citador1, usuario);
//			usuario = FPUsuario.consultarPorLogin(medico1.getLogin());
//			assertEquals(medico1, usuario);
//			usuario = FPUsuario.consultarPorLogin(citador1.getLogin());
//			assertEquals(citador1, usuario);
//		} catch(Exception e) {
//			fail(e.toString());
//		}
//		
//		try {
//			// Modificamos un usuario que no sea médico
//			citador1.setApellidos("U. L.");
//			citador1.setCorreo("abc@abc.abc");
//			citador1.setLogin("ramoncitador");
//			citador1.setMovil("666777888");
//			citador1.setNombre("Ramón");
//			citador1.setPassword(UtilidadesDominio.encriptarPasswordSHA1("nuevapass"));
//			citador1.setTelefono("999888777");
//			FPUsuario.modificar(citador1);
//			// Comprobamos si los cambios han tenido efecto
//			usuario = FPUsuario.consultar(citador1.getNif());
//			assertEquals(citador1, usuario);
//		} catch(Exception e) {
//			fail(e.toString());
//		}
//				
//		try {
//			// Obtenemos una lista con los médicos de cabecera
//			medicos = FPUsuario.consultarMedicos(new Cabecera());
//			assertTrue(medicos.size() == 2);
//			assertTrue((medicos.get(0).equals(medico2.getNif()) && medicos.get(1).equals(medico3.getNif()))
//			           || (medicos.get(0).equals(medico3.getNif()) && medicos.get(1).equals(medico2.getNif())));
//			// Obtenemos una lista con los médidos pediatras
//			medicos = FPUsuario.consultarMedicos(new Pediatra());
//			assertTrue(medicos.size() == 1);
//			assertTrue(medicos.get(0).equals(medico4.getNif()));
//		} catch(Exception e) {
//			fail(e.toString());
//		}
//		
//		try {
//			// Modificamos un médico
//			medico1.setLogin("nuevologin");
//			medico1.getCalendario().add(periodo3);
//			medico1.setTipoMedico(new Cabecera());
//			FPUsuario.modificar(medico1);
//			// Comprobamos si los cambios han tenido efecto
//			usuario = FPUsuario.consultar(medico1.getNif());
//			assertEquals(medico1, usuario);
//		} catch(Exception e) {
//			e.printStackTrace();
//			fail(e.toString());
//		}
//		
//		try {
//			// Modificamos un usuario de forma incorrecta (login repetido)
//			medico1.setLogin("admin");
//			FPUsuario.modificar(medico1);
//			fail("Se esperaba una excepción SQLException");
//		} catch(SQLException e) {
//		} catch(Exception e) {
//			fail("Se esperaba una excepción SQLException");
//		}
//		
//		try {
//			// Eliminamos un usuario
//			FPUsuario.eliminar(administrador1);
//			// Comprobamos si los cambios han tenido efecto
//			usuario = FPUsuario.consultar(administrador1.getNif());
//			fail("Se esperaba una excepción UsuarioIncorrectoException");
//		} catch(UsuarioIncorrectoException e) {
//		} catch(Exception e) {
//			fail("Se esperaba una excepción UsuarioIncorrectoException");
//		}
//		
//		try {
//			// Eliminamos un médico
//			FPUsuario.eliminar(medico1);
//			// Comprobamos si los cambios han tenido efecto
//			usuario = FPUsuario.consultar(medico1.getNif());
//			fail("Se esperaba una excepción UsuarioIncorrectoException");
//		} catch(UsuarioIncorrectoException e) {
//		} catch(Exception e) {
//			fail("Se esperaba una excepción UsuarioIncorrectoException");
//		}
//		
//		try {
//			// Comprobamos que los usuarios no borrados siguen existiendo
//			usuario = FPUsuario.consultar(citador1.getNif());
//			assertEquals(citador1, usuario);
//			usuario = FPUsuario.consultar(citador1.getLogin(), citador1.getPassword());
//			assertEquals(citador1, usuario);
//			usuario = FPUsuario.consultar(medico2.getNif());
//			assertEquals(medico2, usuario);
//			usuario = FPUsuario.consultar(medico2.getLogin(), medico2.getPassword());
//			assertEquals(medico2, usuario);
//		} catch(Exception e) {
//			fail(e.toString());
//		}
//	}
//	
//	/** Pruebas de la tabla de volantes */
//	@SuppressWarnings("deprecation")
//	public void testVolantes() {
//		Volante volante;
//		
//		try {
//			// Intentamos buscar un volante inexistente
//			FPVolante.consultar(-900);
//			fail("Se esperaba una excepción VolanteNoValidoException");
//		} catch(VolanteNoValidoException e) {
//		} catch(Exception e) {
//			fail("Se esperaba una excepción VolanteNoValidoException");
//		}
//		
//		try {
//			// Insertamos todos los objetos referidos por los volantes
//			FPCentroSalud.insertar(centro1);
//			FPCentroSalud.insertar(centro2);
//			FPUsuario.insertar(medico1);
//			FPUsuario.insertar(medico2);
//			FPUsuario.insertar(medico3);
//			FPBeneficiario.insertar(beneficiario1);
//			FPBeneficiario.insertar(beneficiario2);
//			FPCita.insertar(cita1);
//			FPCita.insertar(cita2);
//			// Insertamos varios volantes correctos
//			// (y repetidos, que se permite)
//			FPVolante.insertar(volante1);
//			FPVolante.insertar(volante2);
//			FPVolante.insertar(volante1);
//		} catch(Exception e) {
//			fail(e.toString());
//		}
//		
//		try {
//			// Comprobamos que los volantes se han añadido bien
//			volante = FPVolante.consultar(volante1.getId());
//			assertEquals(volante1, volante);
//			volante = FPVolante.consultar(volante2.getId());
//			assertEquals(volante2, volante);
//		} catch(Exception e) {
//			fail(e.toString());
//		}
//
//		try {
//			// Modificamos un volante existente
//			volante1.setCita(cita2);
//			volante1.setEmisor(medico2);
//			volante1.setReceptor(medico1);
//			volante1.setBeneficiario(beneficiario2);
//			volante1.setFechaCaducidad(new Date(2010 - 1900, 4, 4));
//			FPVolante.modificar(volante1);
//			// Comprobamos que los cambios han tenido efecto
//			volante = FPVolante.consultar(volante1.getId());
//			assertEquals(volante1, volante);
//		} catch(Exception e) {
//			fail(e.toString());
//		}
//		
//		try {
//			// Comprobamos que los volantes siguen teniendo los datos correctos
//			volante = FPVolante.consultar(volante1.getId());
//			assertEquals(volante1, volante);
//			volante = FPVolante.consultar(volante2.getId());
//			assertEquals(volante2, volante);
//		} catch(Exception e) {
//			fail(e.toString());
//		}
//	}
//	
//	/** Pruebas de las utilidades de persistencia */
//	public void testUtilidades() {
//		String nif;
//		
//		try {
//			// Añadimos varios beneficiarios y usuarios
//			FPCentroSalud.insertar(centro1);
//			FPCentroSalud.insertar(centro2);
//			FPUsuario.insertar(medico1);
//			FPUsuario.insertar(medico2);
//			FPUsuario.insertar(medico3);
//			FPUsuario.insertar(administrador1);
//			FPBeneficiario.insertar(beneficiario1);
//			FPBeneficiario.insertar(beneficiario2);
//			FPBeneficiario.insertar(beneficiario3);
//		} catch(Exception e) {
//			fail(e.toString());
//		}
//		
//		try {
//			// Comprobamos que existe el NIF de un beneficiario
//			assertTrue(UtilidadesPersistencia.existeNIF(beneficiario3.getNif()));
//			// Comprobamos que existe el NIF de un usuario
//			assertTrue(UtilidadesPersistencia.existeNIF(administrador1.getNif()));
//			// Comprobamos que no existe un NIF aleatorio
//			assertFalse(UtilidadesPersistencia.existeNIF("11881188P"));
//		} catch(Exception e) {
//			fail(e.toString());
//		}
//		
//		try {
//			// Buscamos un médico de cabecera al azar del centro1
//			nif = UtilidadesPersistencia.obtenerMedicoAleatorioTipoCentro(CategoriasMedico.Cabecera, centro1);
//			assertEquals(medico2.getNif(), nif);
//			// Buscamos un médico de cabecera al azar del centro2
//			nif = UtilidadesPersistencia.obtenerMedicoAleatorioTipoCentro(CategoriasMedico.Cabecera, centro2);
//			assertEquals(medico3.getNif(), nif);
//			// Buscamos un médico al azar que no existe
//			nif = UtilidadesPersistencia.obtenerMedicoAleatorioTipoCentro(CategoriasMedico.Especialista, centro2);
//			assertEquals("", nif);
//		} catch(Exception e) {
//			fail(e.toString());
//		}
//	}
	
}
