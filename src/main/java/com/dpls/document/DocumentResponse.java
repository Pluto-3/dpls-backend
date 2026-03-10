package com.dpls.document;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class DocumentResponse {
    private Long id;
    private String fileName;
    private String fileUrl;
    private LocalDateTime uploadedAt;
}
