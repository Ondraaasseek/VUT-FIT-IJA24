/** 
 * @file Memento.java
 * @brief Class for Memento
 * @author Lukáš Katona (xkaton00) & Ondřej Novotný (xnovot2p)
 */

package ija.ija2023.project.env.history;

import ija.ija2023.project.env.EnvPresenter;

/**
 * Class for Memento
 * @version 1.0
 * @since 2024-05-02
 * 
 * @see SceneSnapshot
 * @see EnvPresenter
 */
public class Memento {
    private SceneSnapshot sceneSnapshot;

    /**
     * Constructor for Memento
     */
    public Memento() {
        this.sceneSnapshot = EnvPresenter.backup();
    }

    /**
     * Restore the scene
     */
    public void restore() {
        EnvPresenter.restore(sceneSnapshot);
    }

    /**
     * Get the scene backup
     * @return SceneSnapshot of the EnvPresenter
     */
    public SceneSnapshot getSceneBackup() {
        return sceneSnapshot;
    }
}