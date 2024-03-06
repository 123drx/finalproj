package daniel.finalproj.Servieses;

import java.util.List;

import org.springframework.stereotype.Service;

import daniel.finalproj.Objects.Principle;
import daniel.finalproj.Repositorys.PrincipleRepository;


@Service
public class PrincipleServies 
{
    private PrincipleRepository Prepo;
    
    public PrincipleServies(PrincipleRepository Screpo)
    {
        this.Prepo = Screpo;
    }

    public void InsertPrincple(Principle s )
    {
        Prepo.insert(s);
    }

    public Principle findbyname(String s)
    {
        return Prepo.findByName(s);
    }

    public Principle UpdatePrinciple(Principle s)
    {
        return Prepo.updatePrinciple(s);
    }

    
   public List<Principle> getAllScheduals()
   {
      return Prepo.findAll();
   }

    


}
