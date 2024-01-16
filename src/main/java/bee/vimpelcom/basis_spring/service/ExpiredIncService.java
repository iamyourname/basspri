package bee.vimpelcom.basis_spring.service;


import bee.vimpelcom.basis_spring.entity.ExpiredInc;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ExpiredIncService {
    List<ExpiredInc> listAllexpireds();

}
