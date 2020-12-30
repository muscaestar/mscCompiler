package codegen.table;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by muscaestar on 12/30/20
 *
 * @author muscaestar
 */
public class SymbolTable {
    // for class-level: [static, field]
    // for local-level: [arg, local]
    private Map<String, Integer> kindCnts;

    private Map<String, SymbolEntry> table;

    public SymbolTable(String kindA, String kindB) {
        kindCnts = new HashMap<>();
        kindCnts.put(kindA, -1);
        kindCnts.put(kindB, -1);
        table = new HashMap<>();
    }

    public String printTable() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("| name           | type           | kind           | index |\n"));
        Comparator<SymbolEntry> cmpByKind = Comparator.comparing(o -> o.kind);
        Comparator<SymbolEntry> cmpByKindIndex = cmpByKind.thenComparing(o -> o.index);
        table.values().stream()
                .sorted(cmpByKindIndex)
                .forEach(e -> sb.append(String.format("| %14s | %14s | %14s | %5d |\n", e.name, e.type, e.kind, e.index)));
        return sb.toString();
    }

    public void add(String name, String type, String kind) {
        int currCnt = kindCnts.get(kind);
        SymbolEntry entry = new SymbolEntry(name, type, kind, ++currCnt);
        kindCnts.replace(kind, currCnt);
        table.put(name, entry);
    }

    public boolean exist(String name) {
        return table.containsKey(name);
    }

    public String getType(String name) {
        return table.get(name).type;
    }

    public String getKind(String name) {
        return table.get(name).kind;
    }

    public int getIndex(String name) {
        return table.get(name).index;
    }

    public void recycle() {
        kindCnts.replaceAll((k,v) -> -1);
        table.clear();
    }

    private class SymbolEntry {
        String name;
        String type;
        String kind;
        int index;

        public SymbolEntry(String name, String type, String kind, int index) {
            this.name = name;
            this.type = type;
            this.kind = kind;
            this.index = index;
        }
    }
}
