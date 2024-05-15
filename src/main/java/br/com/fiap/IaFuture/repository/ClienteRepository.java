package br.com.fiap.IaFuture.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.fiap.IaFuture.model.Cliente;
import br.com.fiap.IaFuture.model.FeedbackCliente;
import br.com.fiap.IaFuture.model.InteracaoCliente;
import io.swagger.v3.oas.annotations.Hidden;

@Hidden
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
 
    @Query(value = "SELECT * FROM Cliente ORDER BY id_cliente DESC LIMIT 10", nativeQuery = true)
    List<Cliente> findLast10();

    @Query("SELECT c FROM Cliente c ORDER BY c.nome ASC")
    List<Cliente> findAllOrderedByName();

    @Query("SELECT ic FROM InteracaoCliente ic WHERE ic.cliente.id_cliente = :id_cliente")
    List<InteracaoCliente> findInteracoesByClienteId(@Param("id_cliente") Long id_cliente);

    @Query("SELECT fc FROM FeedbackCliente fc WHERE fc.cliente.id_cliente = :id_cliente")
    List<FeedbackCliente> findFeedbacksByClienteId(@Param("id_cliente") Long id_cliente);

    @EntityGraph(attributePaths = {"interacoes", "feedbacks"})
    Optional<Cliente> findById(Long id_cliente);

}
