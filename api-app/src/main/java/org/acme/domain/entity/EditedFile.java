package org.acme.domain.entity;

/**
 * @param changeType "ADDED", "MODIFIED", "DELETED"
 */
public record EditedFile(String filePath, String changeType) {
}