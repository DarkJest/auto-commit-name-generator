package org.acme.domain.repository;

import org.acme.domain.entity.EditedFile;

import java.util.List;

public interface AIModelRepository {
    String generateTitle(List<EditedFile> editedFiles);
}