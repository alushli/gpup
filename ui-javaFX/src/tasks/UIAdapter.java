package tasks;

import dtoObjects.TargetRuntimeDTO;
import javafx.application.Platform;

import java.util.Set;
import java.util.function.Consumer;

public class UIAdapter {
    private Consumer<Set<TargetRuntimeDTO>> addToFrozen;
    private Consumer<String> moveFrozenToWaiting;
    private Consumer<String> moveFrozenToSkipped;
    private Consumer<String> moveFrozenToFailed;
    private Consumer<String> moveFrozenToSuccess;
    private Consumer<String> moveFrozenToWarning;
    private Consumer<String> moveFrozenToProgress;
    private Consumer<String> moveWaitingToProgress;
    private Consumer<String> moveWaitingToFailed;
    private Consumer<String> moveWaitingToSuccess;
    private Consumer<String> moveWaitingToWarning;
    private Consumer<String> moveProgressToFailed;
    private Consumer<String> moveProgressToWarning;
    private Consumer<String> moveProgressToSuccess;
    private Consumer<String> finishTargets;
    private Consumer<String> totalTargets;
    private Consumer<Float> progressBar;


    public UIAdapter(Consumer<Set<TargetRuntimeDTO>> addToFrozen,Consumer<String> moveFrozenToWaiting, Consumer<String> moveFrozenToSkipped, Consumer<String> moveFrozenToFailed,
                     Consumer<String> moveFrozenToSuccess, Consumer<String> moveFrozenToWarning,  Consumer<String> moveFrozenToProgress, Consumer<String> moveWaitingToProgress,
                     Consumer<String> moveWaitingToFailed, Consumer<String> moveWaitingToSuccess, Consumer<String> moveWaitingToWarning, Consumer<String> moveProgressToFailed,
                     Consumer<String> moveProgressToWarning,Consumer<String> moveProgressToSuccess, Consumer<String> finishTargets, Consumer<String> totalTargets, Consumer<Float> progressBar){
        this.addToFrozen = addToFrozen;
        this.moveFrozenToSkipped = moveFrozenToSkipped;
        this.moveFrozenToFailed = moveFrozenToFailed;
        this.moveFrozenToSuccess = moveFrozenToSuccess;
        this.moveFrozenToWarning = moveFrozenToWarning;
        this.moveFrozenToProgress = moveFrozenToProgress;
        this.moveWaitingToProgress = moveWaitingToProgress;
        this.moveWaitingToFailed = moveWaitingToFailed;
        this.moveWaitingToSuccess = moveWaitingToSuccess;
        this.moveWaitingToWarning = moveWaitingToWarning;
        this.moveProgressToFailed = moveWaitingToFailed;
        this.moveProgressToWarning = moveProgressToWarning;
        this.moveProgressToSuccess = moveProgressToSuccess;
        this.finishTargets = finishTargets;
        this.totalTargets = totalTargets;
        this.progressBar = progressBar;
    }

    public void addTargetToFrozen(Set<TargetRuntimeDTO> targetRuntimeDTOSet) {
        Platform.runLater(
                () -> {
                    this.addToFrozen.accept(targetRuntimeDTOSet);
                }
        );
    }

    public void frozenToWaiting(String name) {
        Platform.runLater(
                () -> {
                    this.moveFrozenToWaiting.accept(name);
                }
        );
    }

    public void frozenToSkipped(String name) {
        Platform.runLater(
                () -> {
                    this.moveFrozenToSkipped.accept(name);
                }
        );
    }
    public void frozenToFailed(String name) {
        Platform.runLater(
                () -> {
                    this.moveFrozenToFailed.accept(name);
                }
        );
    }
    public void frozenToSuccess(String name) {
        Platform.runLater(
                () -> {
                    this.moveFrozenToSuccess.accept(name);
                }
        );
    }
    public void frozenToWarning(String name) {
        Platform.runLater(
                () -> {
                    this.moveFrozenToWarning.accept(name);
                }
        );
    }
    public void frozenToProgress(String name) {
        Platform.runLater(
                () -> {
                    this.moveFrozenToProgress.accept(name);
                }
        );
    }
    public void waitingToProgress(String name) {
        Platform.runLater(
                () -> {
                    this.moveWaitingToProgress.accept(name);
                }
        );
    }
    public void waitingToFailed(String name) {
        Platform.runLater(
                () -> {
                    this.moveWaitingToFailed.accept(name);
                }
        );
    }
    public void waitingToSuccess(String name) {
        Platform.runLater(
                () -> {
                    this.moveWaitingToSuccess.accept(name);
                }
        );
    }
    public void waitingToWarning(String name) {
        Platform.runLater(
                () -> {
                    this.moveWaitingToWarning.accept(name);
                }
        );
    }
    public void progressToFailed(String name) {
        Platform.runLater(
                () -> {
                    this.moveProgressToFailed.accept(name);
                }
        );
    }
    public void progressToWarning(String name) {
        Platform.runLater(
                () -> {
                    this.moveProgressToWarning.accept(name);
                }
        );
    }
    public void progressToSuccess(String name) {
        Platform.runLater(
                () -> {
                    this.moveProgressToSuccess.accept(name);
                }
        );
    }

    public void updateFinishTargets(String count) {
        Platform.runLater(
                () -> {
                    finishTargets.accept(count);
                }
        );
    }

    public void updateTotalTargets(String count) {
        Platform.runLater(
                () -> {
                    totalTargets.accept(count);
                }
        );
    }

    public void updateProgressBar(float count) {
        Platform.runLater(
                () -> {
                    progressBar.accept(count);
                }
        );
    }



}
