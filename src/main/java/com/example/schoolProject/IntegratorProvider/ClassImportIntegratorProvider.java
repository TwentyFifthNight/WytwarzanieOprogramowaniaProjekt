package com.example.schoolProject.IntegratorProvider;

import com.example.schoolProject.domain.StudentListRecord;
import io.hypersistence.utils.hibernate.type.util.ClassImportIntegrator;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.jpa.boot.spi.IntegratorProvider;

import java.util.List;

public class ClassImportIntegratorProvider implements IntegratorProvider {
    @Override
    public List<Integrator> getIntegrators() {
        return List.of(new ClassImportIntegrator(List.of((StudentListRecord.class))));
    }
}
