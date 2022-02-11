package components.tasks;

import components.dtoObjects.TargetRuntimeDTOFX;
import javafx.application.Platform;
import java.util.Set;
import java.util.function.Consumer;

public class UIAdapter {
    private Consumer<Set<TargetRuntimeDTOFX>> addToFrozen;
    private Consumer<Set<TargetRuntimeDTOFX>> addToWaiting;
    private Consumer<Set<TargetRuntimeDTOFX>> addToProcess;
    private Consumer<Set<TargetRuntimeDTOFX>> addToSkipped;
    private Consumer<Set<TargetRuntimeDTOFX>> addToFailed;
    private Consumer<Set<TargetRuntimeDTOFX>> addToSuccess;
    private Consumer<Set<TargetRuntimeDTOFX>> addToWarning;
    private Consumer<String> finishTargets;
    private Consumer<String> totalTargets;
    private Consumer<Float> progressBar;

    public UIAdapter(Consumer<Set<TargetRuntimeDTOFX>> addToFrozen, Consumer<Set<TargetRuntimeDTOFX>> addToWaiting, Consumer<Set<TargetRuntimeDTOFX>> addToProcess, Consumer<Set<TargetRuntimeDTOFX>> addToSkipped,
                     Consumer<Set<TargetRuntimeDTOFX>> addToFailed, Consumer<Set<TargetRuntimeDTOFX>> addToWarning, Consumer<Set<TargetRuntimeDTOFX>> addToSuccess, Consumer<String> finishTargets, Consumer<String> totalTargets,
                     Consumer<Float> progressBar){
        this.addToFrozen = addToFrozen;
        this.addToWaiting = addToWaiting;
        this.addToProcess = addToProcess;
        this.addToSkipped = addToSkipped;
        this.addToWarning = addToWarning;
        this.addToFailed = addToFailed;
        this.addToSuccess = addToSuccess;
        this.finishTargets = finishTargets;
        this.totalTargets = totalTargets;
        this.progressBar = progressBar;
    }

    public void addFrozen(Set<TargetRuntimeDTOFX> targetRuntimeDTOSet) {
        Platform.runLater(
                () -> {
                    this.addToFrozen.accept(targetRuntimeDTOSet);
                }
        );
    }
    public void addWaiting(Set<TargetRuntimeDTOFX> targetRuntimeDTOSet) {
        Platform.runLater(
                () -> {
                    this.addToWaiting.accept(targetRuntimeDTOSet);
                }
        );
    }
    public void addProcess(Set<TargetRuntimeDTOFX> targetRuntimeDTOSet) {
        Platform.runLater(
                () -> {
                    this.addToProcess.accept(targetRuntimeDTOSet);
                }
        );
    }
    public void addSkipped(Set<TargetRuntimeDTOFX> targetRuntimeDTOSet) {
        Platform.runLater(
                () -> {
                    this.addToSkipped.accept(targetRuntimeDTOSet);
                }
        );
    }
    public void addFailed(Set<TargetRuntimeDTOFX> targetRuntimeDTOSet) {
        Platform.runLater(
                () -> {
                    this.addToFailed.accept(targetRuntimeDTOSet);
                }
        );
    }
    public void addSuccess(Set<TargetRuntimeDTOFX> targetRuntimeDTOSet) {
        Platform.runLater(
                () -> {
                    this.addToSuccess.accept(targetRuntimeDTOSet);
                }
        );
    }
    public void addWarning(Set<TargetRuntimeDTOFX> targetRuntimeDTOSet) {
        Platform.runLater(
                () -> {
                    this.addToWarning.accept(targetRuntimeDTOSet);
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
