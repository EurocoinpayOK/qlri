package commands.qubic;

import resp.general.ResponseAbstract;
import resp.general.ResponseSuccess;
import commands.Command;
import commands.param.CallValidator;
import commands.param.ParameterValidator;
import commands.param.validators.TryteValidator;
import main.Main;
import main.Persistence;
import qubic.QubicWriter;

import java.util.Map;

public class CommandQubicAssemblyAdd extends CommandQubicAbstract {

    public static final CommandQubicAssemblyAdd instance = new CommandQubicAssemblyAdd();

    private static final CallValidator CV = new CallValidator(new ParameterValidator[]{
            new TryteValidator(1, 81).setName("qubic handle").setDescription("the qubic to whose assembly the oracle shall be added (finds the qubic starting with this tryte sequence)"),
            new TryteValidator(81, 81).setName("oracle id").setDescription("IAM stream identity of the oracle to add to the assembly"),
    });

    @Override
    public CallValidator getCallValidator() {
        return CV;
    }

    @Override
    public String getName() {
        return "qubic_assembly_add";
    }

    @Override
    public String getAlias() {
        return "qaa";
    }

    @Override
    public String getDescription() {
        return "Adds an oracle to the assembly as preparation for '" + CommandQubicAssemble.instance.getName() + "'.";
    }

    @Override
    public void terminalPostPerformAction(ResponseAbstract response, Persistence persistence, String[] par) {
        String oracleID = par[2];
        Main.println("added oracle '" + oracleID + "' to qubic assembly");
    }

    @Override
    public ResponseAbstract perform(Persistence persistence, Map<String, Object> parMap) {

        String qubicHandle = (String)parMap.get("qubic_handle");
        String oracleID = (String)parMap.get("oracle_id");

        QubicWriter qw = persistence.findQubicWriterByHandle(qubicHandle);

        if (qw != null)
            qw.getAssembly().add(oracleID);

        return new ResponseSuccess();
    }

    @Override
    public boolean isRemotelyAvailable() {
        return false;
    }
}
