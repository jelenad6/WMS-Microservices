package wms.inventory_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import wms.inventory_service.model.DispatchNote;

public interface DispatchNoteRepository
        extends JpaRepository<DispatchNote, Long> {

}