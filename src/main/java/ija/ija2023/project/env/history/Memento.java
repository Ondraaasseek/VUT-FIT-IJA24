package ija.ija2023.project.env.history;

import ija.ija2023.project.env.EnvPresenter;

public class Memento {
    private SceneSnapshot sceneSnapshot;

    public Memento() {
        this.sceneSnapshot = EnvPresenter.backup();
    }

    public void restore() {
        EnvPresenter.restore(sceneSnapshot);
    }

    public SceneSnapshot getSceneBackup() {
        return sceneSnapshot;
    }
}