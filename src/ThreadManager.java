import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.*;

/**
 * Manages the execution of chat and friend request tasks.
 */
public class ThreadManager {
    private static final Logger logger = Logger.getLogger(ThreadManager.class.getName());
    private final ExecutorService executor;

    /**
     * Initializes the ThreadManager with a fixed thread pool
     *
     * @param threadPoolSize The number of threads in the pool
     */
    public ThreadManager(int threadPoolSize) {
        this.executor = Executors.newFixedThreadPool(threadPoolSize);
    }

    /**
     * Submits a friend request task to the executor
     *
     * @param sender   The student sending the friend request.
     * @param receiver The student receiving the friend request.
     */
    public void sendFriendRequest(UniversityStudent sender, UniversityStudent receiver) {
        executor.execute(new FriendRequestThread(sender, receiver));
    }

    /**
     * Submits a chat task to the executor.
     *
     * @param sender   The student sending the message.
     * @param receiver The student receiving the message.
     * @param message  The message content.
     */
    public void sendMessage(UniversityStudent sender, UniversityStudent receiver, String message) {
        executor.execute(new ChatThread(sender, receiver, message));
    }

    /**
     * Shuts down the executor service
     */
    public void shutdown() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
                if (!executor.awaitTermination(60, TimeUnit.SECONDS))
                    logger.severe("Executor did not terminate.");
            }
        } catch (InterruptedException ie) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Configures the logger for ThreadManager and its tasks.
     */
    public static void configureLogger() {
        Logger rootLogger = Logger.getLogger("");
        Handler[] handlers = rootLogger.getHandlers();

        for (Handler handler : handlers) {
            rootLogger.removeHandler(handler);
        }

        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.INFO);
        consoleHandler.setFormatter(new SimpleFormatter());
        rootLogger.addHandler(consoleHandler);
        rootLogger.setLevel(Level.INFO);
    }
}
