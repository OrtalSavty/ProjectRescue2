# GitHub Copilot Instructions for ProjectRescue2

1. **Project & Package:** All generated Java code must belong to the `rescue` package.
2. **No External Libraries:** Use only standard Java data structures and the Java Collections Framework (e.g., ArrayList, HashMap, LinkedList, TreeSet, PriorityQueue, HashSet). Do NOT use any external or third-party dependencies.
3. **Console Output:** Do NOT print anything to standard output (`System.out.println`) during normal execution. The auto-checker is strictly sensitive to outputs.
4. **Formatting Strictness:**
   - All `double` values in reports must be formatted with exactly one decimal place.
   - Do not add any spaces before or after commas (`,`) or equals signs (`=`) in file outputs, logs, or string parsing.
5. **Naming & Signatures:** Strictly preserve all class names, method signatures, variable casing, and formatting exactly as prompted, as the assignment relies on an automated testing suite.