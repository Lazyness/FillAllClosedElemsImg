package prog.ua.prog.core.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NavigateFlags {
    private boolean upFlag;
    private boolean downFlag;
    private boolean leftFlag;
    private boolean rightFlag;

    public void initFlags(){
        upFlag = true;
        downFlag = true;
        leftFlag = true;
        rightFlag = true;
    }

    public void resetFlags(){
        upFlag = false;
        downFlag = false;
        leftFlag = false;
        rightFlag = false;
    }
}
