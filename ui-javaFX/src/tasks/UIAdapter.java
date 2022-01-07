package tasks;

import dtoObjects.TargetRuntimeDTO;
import javafx.application.Platform;
import java.util.Collection;
import java.util.Set;
import java.util.function.Consumer;

public class UIAdapter {
    private Consumer<Collection<TargetRuntimeDTO>> addToFrozen;
    private Consumer<Set<TargetRuntimeDTO>> addToWaiting;
    private Consumer<Set<TargetRuntimeDTO>> addToProcess;
    private Consumer<Set<TargetRuntimeDTO>> addToSkipped;
    private Consumer<Set<TargetRuntimeDTO>> addToFailed;
    private Consumer<Set<TargetRuntimeDTO>> addToSuccess;
    private Consumer<Set<TargetRuntimeDTO>> addToWarning;
    private Consumer<String> finishTargets;
    private Consumer<String> totalTargets;
    private Consumer<Float> progressBar;

    public UIAdapter(Consumer<Collection<TargetRuntimeDTO>> addToFrozen, Consumer<Set<TargetRuntimeDTO>> addToWaiting, Consumer<Set<TargetRuntimeDTO>> addToProcess, Consumer<Set<TargetRuntimeDTO>> addToSkipped,
                     Consumer<Set<TargetRuntimeDTO>> addToFailed, Consumer<Set<TargetRuntimeDTO>> addToWarning, Consumer<Set<TargetRuntimeDTO>> addToSuccess, Consumer<String> finishTargets, Consumer<String> totalTargets, Consumer<Float> progressBar){
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

    public void addFrozen(Collection<TargetRuntimeDTO> targetRuntimeDTOSet) {
        Platform.runLater(
                () -> {
                    this.addToFrozen.accept(targetRuntimeDTOSet);
                }
        );
    }
    public void addWaiting(Set<TargetRuntimeDTO> targetRuntimeDTOSet) {
        Platform.runLater(
                () -> {
                    this.addToWaiting.accept(targetRuntimeDTOSet);
                }
        );
    }
    public void addProcess(Set<TargetRuntimeDTO> targetRuntimeDTOSet) {
        Platform.runLater(
                () -> {
                    this.addToProcess.accept(targetRuntimeDTOSet);
                }
        );
    }
    public void addSkipped(Set<TargetRuntimeDTO> targetRuntimeDTOSet) {
        Platform.runLater(
                () -> {
                    this.addToSkipped.accept(targetRuntimeDTOSet);
                }
        );
    }
    public void addFailed(Set<TargetRuntimeDTO> targetRuntimeDTOSet) {
        Platform.runLater(
                () -> {
                    this.addToFailed.accept(targetRuntimeDTOSet);
                }
        );
    }
    public void addSuccess(Set<TargetRuntimeDTO> targetRuntimeDTOSet) {
        Platform.runLater(
                () -> {
                    this.addToSuccess.accept(targetRuntimeDTOSet);
                }
        );
    }
    public void addWarning(Set<TargetRuntimeDTO> targetRuntimeDTOSet) {
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
