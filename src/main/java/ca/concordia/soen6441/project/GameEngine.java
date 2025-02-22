package ca.concordia.soen6441.project;

public class GameEngine {
    private Phase gamePhase;

    public void setPhase(Phase p_phase) {
        gamePhase = p_phase;
    }

    public void start() {
        // Can change the state of the Context (GameEngine) object, e.g.
        setPhase(new PreLoad(this));
        setPhase(new PostLoad(this));
        setPhase(new PlaySetup(this));
        setPhase(new Reinforcement(this));

        // Can trigger State-dependent behavior by using
        // the methods defined in the State (Phase) object, e.g.
        //        gamePhase.loadMap();
        //        gamePhase.next();
    }
}