# Parametri pentru compilare.
JFLAGS := -g

# Directorul care conține sursele voastre, și cel unde punem binarele.
SRC_DIR := .
OUT_DIR := .

# Compilăm *toate* sursele găsite în $(SRC_DIR).
# Modificați doar dacă vreți să compilați alte surse.
JAVA_SRC := $(wildcard $(SRC_DIR)/*.java)

JAVA_CLASSES := $(JAVA_SRC:$(SRC_DIR)/%.java=$(OUT_DIR)/%.class)
TARGETS := $(CC_EXECS) $(JAVA_CLASSES)

.PHONY: build clean

build: $(TARGETS)

clean:
	rm -f $(TARGETS) $(OUT_DIR)/*.class

run:
	java Main

# Reguli pentru compilare. Probabil nu e nevoie să le modificați.

$(JAVA_CLASSES): $(OUT_DIR)/%.class: $(SRC_DIR)/%.java
	javac $< -d $(OUT_DIR) -cp $(SRC_DIR) $(JFLAGS)
