package com.mcal.androlib.meta;

import org.yaml.snakeyaml.constructor.AbstractConstruct;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.ScalarNode;
import org.yaml.snakeyaml.nodes.Tag;

import java.util.ArrayList;
import java.util.List;

public class ClassSafeConstructor extends Constructor {
    protected final List<Class<?>> allowableClasses = new ArrayList<>();

    public ClassSafeConstructor() {
        this.yamlConstructors.put(Tag.STR, new ConstructStringEx());

        this.allowableClasses.add(MetaInfo.class);
        this.allowableClasses.add(PackageInfo.class);
        this.allowableClasses.add(UsesFramework.class);
        this.allowableClasses.add(VersionInfo.class);
    }

    protected Object newInstance(Node node) {
        if (this.yamlConstructors.containsKey(node.getTag()) || this.allowableClasses.contains(node.getType())) {
            return super.newInstance(node);
        }
        throw new YAMLException("Invalid Class attempting to be constructed: " + node.getTag());
    }

    protected Object finalizeConstruction(Node node, Object data) {
        if (this.yamlConstructors.containsKey(node.getTag()) || this.allowableClasses.contains(node.getType())) {
            return super.finalizeConstruction(node, data);
        }

        return this.newInstance(node);
    }

    private class ConstructStringEx extends AbstractConstruct {
        public Object construct(Node node) {
            String val = constructScalar((ScalarNode) node);
            return YamlStringEscapeUtils.unescapeString(val);
        }
    }
}