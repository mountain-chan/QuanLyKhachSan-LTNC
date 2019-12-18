package model;

public class Checkbox {

    private boolean checked;
    private String label;

    public Checkbox(String label) {
        this.checked = false;
        this.label = label;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

}
