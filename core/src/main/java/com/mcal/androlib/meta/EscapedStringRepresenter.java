package com.mcal.androlib.meta;

import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.representer.Representer;

public class EscapedStringRepresenter extends Representer {
    public EscapedStringRepresenter() {
        RepresentStringEx representStringEx = new RepresentStringEx();
        multiRepresenters.put(String.class, representStringEx);
        representers.put(String.class, representStringEx);
    }

    private class RepresentStringEx extends RepresentString {

        @Override
        public Node representData(Object data) {
            return super.representData(YamlStringEscapeUtils.escapeString(data.toString()));
        }
    }
}