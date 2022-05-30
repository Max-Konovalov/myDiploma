package ru.mkonovalov.jurdoc.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.mkonovalov.jurdoc.domain.entity.DocumentPackage;

public interface DocumentPackageRepository extends JpaRepository<DocumentPackage, Long>, JpaSpecificationExecutor<DocumentPackage> {
}
