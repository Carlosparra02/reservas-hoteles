import java.util.ArrayList;
import java.util.List;

class Usuario {
    private String nombre;
    private String email;
    private String password;
    private List<Reserva> reservas;

    public Usuario(String nombre, String email, String password) {
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.reservas = new ArrayList<>();
    }

    public void hacerReserva(Reserva reserva) {
        this.reservas.add(reserva);
    }

    public void cancelarReserva(Reserva reserva) {
        this.reservas.remove(reserva);
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    // Métodos getter y setter
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "Nombre: " + nombre + ", Email: " + email;
    }

    public String getNombre() {
        return nombre;
    }
}

class Habitacion {
    private String tipo;
    private double precioPorNoche;
    private int maxHuespedes;
    private List<String> comodidades;
    private boolean disponible;

    public Habitacion(String tipo, double precioPorNoche, int maxHuespedes, List<String> comodidades) {
        this.tipo = tipo;
        this.precioPorNoche = precioPorNoche;
        this.maxHuespedes = maxHuespedes;
        this.comodidades = comodidades;
        this.disponible = true;
    }

    // Getters y setters
    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    @Override
    public String toString() {
        return "Tipo: " + tipo + ", Precio por noche: " + precioPorNoche + ", Máx. huéspedes: " + maxHuespedes
                + ", Comodidades: " + comodidades + ", Disponible: " + disponible;
    }

    public String getTipo() {
        return tipo;
    }
}

class Reserva {
    private Usuario usuario;
    private Habitacion habitacion;
    private String fechaInicio;
    private String fechaFin;

    public Reserva(Usuario usuario, Habitacion habitacion, String fechaInicio, String fechaFin) {
        this.usuario = usuario;
        this.habitacion = habitacion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    // Getters y setters
    public Habitacion getHabitacion() {
        return habitacion;
    }

    @Override
    public String toString() {
        return "Usuario: " + usuario.getNombre() + ", Habitación: " + habitacion.getTipo() + ", Fecha inicio: "
                + fechaInicio + ", Fecha fin: " + fechaFin;
    }
}

class SistemaReservas {
    private List<Usuario> usuarios;
    private List<Habitacion> habitaciones;
    private List<Reserva> reservas;

    public SistemaReservas() {
        this.usuarios = new ArrayList<>();
        this.habitaciones = new ArrayList<>();
        this.reservas = new ArrayList<>();
    }

    public void registrarUsuario(String nombre, String email, String password) {
        Usuario nuevoUsuario = new Usuario(nombre, email, password);
        this.usuarios.add(nuevoUsuario);
    }

    public Usuario iniciarSesion(String email, String password) {
        for (Usuario usuario : this.usuarios) {
            if (usuario.getEmail().equals(email) && usuario.getPassword().equals(password)) {
                return usuario;
            }
        }
        return null;
    }

    public List<Habitacion> buscarHabitacionesDisponibles(String fechaInicio, String fechaFin) {
        List<Habitacion> habitacionesDisponibles = new ArrayList<>();
        for (Habitacion habitacion : this.habitaciones) {
            if (habitacion.isDisponible()) {
                habitacionesDisponibles.add(habitacion);
            }
        }
        return habitacionesDisponibles;
    }

    public void hacerReserva(Usuario usuario, Habitacion habitacion, String fechaInicio, String fechaFin) {
        Reserva nuevaReserva = new Reserva(usuario, habitacion, fechaInicio, fechaFin);
        usuario.hacerReserva(nuevaReserva);
        habitacion.setDisponible(false);
        this.reservas.add(nuevaReserva);
    }

    public void cancelarReserva(Usuario usuario, Reserva reserva) {
        usuario.cancelarReserva(reserva);
        reserva.getHabitacion().setDisponible(true);
        this.reservas.remove(reserva);
    }

    public List<Reserva> verReservasUsuario(Usuario usuario) {
        return usuario.getReservas();
    }

    // Getters para las listas de usuarios, habitaciones y reservas
    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public List<Habitacion> getHabitaciones() {
        return habitaciones;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }
}

public class Main {
    public static void main(String[] args) {
        SistemaReservas sistema = new SistemaReservas();

        // Registrar usuario
        sistema.registrarUsuario("carlos parra", "carlos02@email.com", "carlos02");

        // Iniciar sesión
        Usuario usuarioActual = sistema.iniciarSesion("carlos02@email.com", "carlos02");

        // Añadir habitaciones
        Habitacion habitacion1 = new Habitacion("Individual", 50, 2, List.of("TV", "Wifi"));
        Habitacion habitacion2 = new Habitacion("Doble", 100, 4, List.of("TV", "Wifi", "Cama extra"));
        sistema.getHabitaciones().add(habitacion1);
        sistema.getHabitaciones().add(habitacion2);

        // bienvenido
        System.out.println("*** BIENVENIDO AH SU RESERVA DE HOTELES *** ");

        // Buscar habitaciones disponibles
        System.out.println("Habitaciones disponibles:");
        for (Habitacion habitacion : sistema.buscarHabitacionesDisponibles("2024-05-01", "2024-05-03")) {
            System.out.println(habitacion.toString());
        }

        // Hacer reserva
        sistema.hacerReserva(usuarioActual, habitacion1, "2024-05-01", "2024-05-03");

        // Ver reservas del usuario
        System.out.println("Reservas del usuario:");
        for (Reserva reserva : sistema.verReservasUsuario(usuarioActual)) {
            System.out.println(reserva.toString());
        }

        // Cancelar reserva
        Reserva reservaACancelar = usuarioActual.getReservas().get(0);
        sistema.cancelarReserva(usuarioActual, reservaACancelar);

        // Verificar que la reserva haya sido cancelada
        System.out.println("Reservas del usuario después de cancelar:");
        for (Reserva reserva : sistema.verReservasUsuario(usuarioActual)) {
            System.out.println(reserva.toString());
        }
    }
}
