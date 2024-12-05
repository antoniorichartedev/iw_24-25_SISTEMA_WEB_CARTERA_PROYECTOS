package proyectum.data.servicios;

import proyectum.data.entidades.Formulario;
import proyectum.data.repositorios.FormularioRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class FormularioService {
    private final FormularioRepository formularioRepository;

    public FormularioService(FormularioRepository formularioRepository){this.formularioRepository = formularioRepository;}
    public List<Formulario> getAllFormularios(){return (List<Formulario>) formularioRepository.findAll(); }

    public Optional<Formulario> getFormularioById(UUID id){return formularioRepository.findById(id);}

    public Formulario saveFormulario(Formulario formulario) {
        return formularioRepository.save(formulario);
    }

    public void deleteFormulario(UUID id) {
        formularioRepository.deleteById(id);
    }

}
