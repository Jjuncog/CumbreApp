package app;
import models.*;

import java.time.LocalDate;

public class Main {

	public static void main(String[] args) {
		M04_Prestamos prestamo = new M04_Prestamos(111, 111, "juanito", "321333", LocalDate.now(), 4, 1001, 4);

		System.out.println(prestamo.consultar_prestamo());
	}

}
