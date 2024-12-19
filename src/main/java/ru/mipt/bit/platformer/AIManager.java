package ru.mipt.bit.platformer;

import org.springframework.stereotype.Component;
import ru.mipt.bit.platformer.ai.AIMotionTankController;

import java.util.List;

@Component
public class AIManager {
    private final List<AIMotionTankController> aiControllers;

    public AIManager(List<AIMotionTankController> aiControllers) {
        this.aiControllers = aiControllers;
    }

    public void updateAI(float deltaTime) {
        for (AIMotionTankController controller : aiControllers) {
            controller.update(deltaTime);
        }
    }
}
