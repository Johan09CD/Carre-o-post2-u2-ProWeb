package co.edu.udes.carreño.post2u12.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.udes.carreño.post2u12.domain.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
