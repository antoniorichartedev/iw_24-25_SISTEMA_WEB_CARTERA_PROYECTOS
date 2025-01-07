package projectum.data.servicios;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projectum.data.entidades.Formulario;
import projectum.data.repositorios.FormularioRepository;
import projectum.data.repositorios.UsuarioRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FormularioService {
    private final FormularioRepository formularioRepository;
    private final UsuarioRepository usuarioRepository;

    public FormularioService(FormularioRepository formularioRepository, UsuarioRepository usuarioRepository){
        this.formularioRepository = formularioRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<Formulario> getAllFormularios(){return (List<Formulario>) formularioRepository.findAll(); }

    public Optional<Formulario> getFormularioById(UUID id){return formularioRepository.findById(id);}

    public List<Formulario> getFormulariosByProyectoId(UUID id){return formularioRepository.findByProyectoId(id);}

    @Transactional
    public void saveFormulario(Formulario formulario) {
        Optional<Formulario> existente = formularioRepository.findById(formulario.getId());
        if (existente.isEmpty()) {
            formularioRepository.save(formulario);
        }
    }


    public void deleteFormulario(UUID id) {
        formularioRepository.deleteById(id);
    }

}
