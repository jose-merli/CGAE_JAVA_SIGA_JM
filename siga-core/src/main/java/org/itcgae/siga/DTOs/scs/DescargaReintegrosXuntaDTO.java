package org.itcgae.siga.DTOs.scs;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DescargaReintegrosXuntaDTO {
    private List<String> idFactsList = new ArrayList<>();
}
