package com.ReVive.cl.ReVive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.ReVive.cl.ReVive.model.Camiones;
import com.ReVive.cl.ReVive.model.CategoriasProducto;
import com.ReVive.cl.ReVive.model.CategoriasResiduos;
import com.ReVive.cl.ReVive.model.Comuna;
import com.ReVive.cl.ReVive.model.DetalleVenta;
import com.ReVive.cl.ReVive.model.Producto;
import com.ReVive.cl.ReVive.model.Region;
import com.ReVive.cl.ReVive.model.Residuos;
import com.ReVive.cl.ReVive.model.Roles;
import com.ReVive.cl.ReVive.model.Sector;
import com.ReVive.cl.ReVive.model.Sucursal;
import com.ReVive.cl.ReVive.model.Usuarios;
import com.ReVive.cl.ReVive.model.Ventas;
import com.ReVive.cl.ReVive.repository.CamionesRepository;
import com.ReVive.cl.ReVive.repository.CategoriasProductoRepository;
import com.ReVive.cl.ReVive.repository.CategoriasResiduosRepository;
import com.ReVive.cl.ReVive.repository.ComunaRepository;
import com.ReVive.cl.ReVive.repository.DetalleVentaRepository;
import com.ReVive.cl.ReVive.repository.ProductoRepository;
import com.ReVive.cl.ReVive.repository.RegionRepository;
import com.ReVive.cl.ReVive.repository.ResiduosRepository;
import com.ReVive.cl.ReVive.repository.RolesRepository;
import com.ReVive.cl.ReVive.repository.SectorRepository;
import com.ReVive.cl.ReVive.repository.SucursalRepository;
import com.ReVive.cl.ReVive.repository.UsuariosRepository;
import com.ReVive.cl.ReVive.repository.VentasRepository;

import net.datafaker.Faker;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Component
@Profile("test")
public class DataLoader implements CommandLineRunner {

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private ComunaRepository comunaRepository;

    @Autowired
    private SectorRepository sectorRepository;

    @Autowired
    private SucursalRepository sucursalRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private CamionesRepository camionesRepository;

    @Autowired
    private CategoriasProductoRepository categoriasProductoRepository;

    @Autowired
    private CategoriasResiduosRepository categoriasResiduosRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ResiduosRepository residuosRepository;

    @Autowired
    private VentasRepository ventasRepository;

    @Autowired
    private DetalleVentaRepository detalleVentaRepository;

    private Faker faker = new Faker();
    private Random random = new Random();

    @Override
    public void run(String... args) throws Exception {

        for (int i = 1; i <= 3; i++) {
            Region region = new Region();
            region.setCodigoRegion(i);
            region.setNombreRegion(faker.address().state());
            regionRepository.save(region);
        }
        List<Region> regiones = regionRepository.findAll();

        for (int i = 1; i <= 5; i++) {
            Comuna comuna = new Comuna();
            comuna.setCodigoComuna(100 + i);
            comuna.setNombreComuna(faker.address().cityName());
            comuna.setRegion(regiones.get(random.nextInt(regiones.size())));
            comunaRepository.save(comuna);
        }
        List<Comuna> comunas = comunaRepository.findAll();

        for (int i = 1; i <= 3; i++) {
            Sector sector = new Sector();
            sector.setNombreSector(faker.company().industry());
            sector.setDireccionSector(faker.address().streetAddress());
            sector.setComuna(comunas.get(random.nextInt(comunas.size())));
            sectorRepository.save(sector);
        }
        List<Sector> sectores = sectorRepository.findAll();

        for (int i = 1; i <= 3; i++) {
            Sucursal sucursal = new Sucursal();
            sucursal.setRazonSocialSucursal(faker.company().name());
            sucursal.setDireccionSucursal(faker.address().fullAddress());
            sucursalRepository.save(sucursal);
        }
        List<Sucursal> sucursales = sucursalRepository.findAll();

        String[] rolesArray = {"ADMIN", "USUARIO", "RECOLECTOR"};
        for (String nombreRol : rolesArray) {
            Roles rol = new Roles();
            rol.setNombreRoles(nombreRol);
            rolesRepository.save(rol);
        }
        List<Roles> roles = rolesRepository.findAll();

        for (int i = 1; i <= 5; i++) {
            Usuarios usuario = new Usuarios();
            usuario.setRunUsuario(faker.idNumber().valid());
            usuario.setNombreUsuario(faker.name().firstName());
            usuario.setApellidoUsuario(faker.name().lastName());
            usuario.setDireccionUsuario(faker.address().streetAddress());
            usuario.setCorreoUsuario(faker.internet().emailAddress());
            usuario.setContraseniaUsuario("password123");
            usuario.setSalario(faker.number().randomDouble(2, 100000, 500000));
            usuario.setSucursal(sucursales.get(random.nextInt(sucursales.size())));
            usuario.setRol(roles.get(random.nextInt(roles.size())));
            usuariosRepository.save(usuario);
        }
        List<Usuarios> usuarios = usuariosRepository.findAll();

        for (int i = 1; i <= 3; i++) {
            Camiones camion = new Camiones();
            camion.setPatenteCamion(faker.bothify("??###??"));
            camion.setSector(sectores.get(random.nextInt(sectores.size())));
            camion.setSucursal(sucursales.get(random.nextInt(sucursales.size())));
            camionesRepository.save(camion);
        }

        for (int i = 1; i <= 3; i++) {
            CategoriasProducto catProd = new CategoriasProducto();
            catProd.setNombreCatesProducto(faker.commerce().department());
            categoriasProductoRepository.save(catProd);
        }
        List<CategoriasProducto> categoriasProductos = categoriasProductoRepository.findAll();

        for (int i = 1; i <= 3; i++) {
            CategoriasResiduos catRes = new CategoriasResiduos();
            catRes.setNombreCatesResiduos(faker.lorem().word());
            categoriasResiduosRepository.save(catRes);
        }
        List<CategoriasResiduos> categoriasResiduos = categoriasResiduosRepository.findAll();

        for (int i = 1; i <= 5; i++) {
            Producto producto = new Producto();
            producto.setNombreProducto(faker.commerce().productName());
            producto.setDescripcionProducto(faker.lorem().sentence());
            producto.setValorProducto(faker.number().numberBetween(1000, 50000));
            producto.setStockProducto(faker.number().numberBetween(10, 100));
            producto.setCategoria(categoriasProductos.get(random.nextInt(categoriasProductos.size())));
            producto.setSucursal(sucursales.get(random.nextInt(sucursales.size())));
            productoRepository.save(producto);
        }
        List<Producto> productos = productoRepository.findAll();

        for (int i = 1; i <= 3; i++) {
            Residuos residuos = new Residuos();
            residuos.setCantidadResiduos(faker.number().numberBetween(1, 500));
            residuos.setCategoriaResiduos(categoriasResiduos.get(random.nextInt(categoriasResiduos.size())));
            residuos.setSucursal(sucursales.get(random.nextInt(sucursales.size())));
            residuosRepository.save(residuos);
        }

        for (int i = 1; i <= 5; i++) {
            Ventas venta = new Ventas();
            venta.setFechaVenta(new java.sql.Date(new Date().getTime()));
            venta.setTotalVenta(faker.number().numberBetween(10000, 100000));
            venta.setUsuario(usuarios.get(random.nextInt(usuarios.size())));
            venta.setSucursal(sucursales.get(random.nextInt(sucursales.size())));
            ventasRepository.save(venta);
        }
        List<Ventas> ventas = ventasRepository.findAll();

        for (int i = 1; i <= 10; i++) {
            DetalleVenta detalleVenta = new DetalleVenta();
            detalleVenta.setCantidad(faker.number().numberBetween(1, 10));
            detalleVenta.setPrecioUnitario(faker.number().numberBetween(1000, 10000));
            detalleVenta.setSubtotal(detalleVenta.getCantidad() * detalleVenta.getPrecioUnitario());
            detalleVenta.setVentas(ventas.get(random.nextInt(ventas.size())));
            detalleVenta.setProducto(productos.get(random.nextInt(productos.size())));
            detalleVentaRepository.save(detalleVenta);
        }

    }
}
