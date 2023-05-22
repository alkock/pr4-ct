public enum Metrik {
    MMBÜ, MCDC,EBÜ;

    public String toString(Metrik metrik){
        if(metrik == MMBÜ){
            return "MMBÜ";
        }
        if(metrik == MCDC){
            return "MCDC";
        }
        if(metrik == EBÜ){
            return "EBÜ";
        }
        return null;
    }
}
