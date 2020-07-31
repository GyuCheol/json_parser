package json.task;

public abstract class JsonTask {
    private static final JsonTask[] createdTasks = { new FindSymbolTask() };

    private JsonTaskType taskType;

    public abstract int doTask(String json, int start);

    private JsonTask(JsonTaskType type) {
        this.taskType = type;
    }

    public JsonTaskType getTaskType() {
        return taskType;
    }

    private static class FindSymbolTask extends JsonTask {
        private static final String WHITE_SPACES = " \t\n\r";

        public FindSymbolTask() {
            super(JsonTaskType.FIND_SYMBOL);
        }

        @Override
        public int doTask(String json, int start) {
            return 0;
        }
    }

    public static JsonTask getFindSymbolTask() {
        return createdTasks[0];
    }

}
