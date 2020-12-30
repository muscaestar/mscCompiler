package codegen.table;

/**
 * Created by muscaestar on 12/30/20
 *
 * @author muscaestar
 */
public class VarTable {
    private static VarTable varTable;

    public static VarTable getInstance() {
        if (varTable == null) {
            varTable = new VarTable();
        }
        return varTable;
    }

    private final SymbolTable classLvTable;
    private final SymbolTable localLvTable;

    public VarTable() {
        this.classLvTable = new SymbolTable("static", "field");
        this.localLvTable = new SymbolTable("argument", "local");
    }

    public void recycleLocalLvTable() {
        localLvTable.recycle();
    }

    public void recycleClassLvTable() {
        classLvTable.recycle();
    }

    public void add(String name, String type, String kind) {
        switch (kind) {
            case "static":
            case "field":
                classLvTable.add(name, type, kind);
                break;
            case "argument":
            case "local":
                localLvTable.add(name, type, kind);
                break;
            default:
                throw new IllegalArgumentException("Variable kind invalid: " + kind);
        }
    }

    public boolean exist(String name) {
        return localLvTable.exist(name) || classLvTable.exist(name);
    }

    public String getType(String name) {
        return localLvTable.exist(name) ? localLvTable.getType(name) : classLvTable.getType(name);
    }

    public String getKind(String name) {
        return localLvTable.exist(name) ? localLvTable.getKind(name) : classLvTable.getKind(name);
    }

    public int getIndex(String name) {
        return localLvTable.exist(name) ? localLvTable.getIndex(name) : classLvTable.getIndex(name);
    }

    public void printLocalLvTable() {
        System.out.println(localLvTable.printTable());
    }

    public void printClassLvTable() {
        System.out.println(classLvTable.printTable());
    }
}
