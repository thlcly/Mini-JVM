package com.aaront.exercise.jvm.commands;

import com.aaront.exercise.jvm.ClassFile;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tonyhui
 * @since 17/6/17
 */
public class CommandParser {

    public static List<AbstractCommand> parse(ClassFile classFile, String codes) {
        CommandIterator iter = new CommandIterator(codes);
        List<AbstractCommand> commands = new ArrayList<>();
        while (iter.hasNext()) {
            String code = iter.next2CharAsString().toUpperCase();
            AbstractCommand command = _handle(Command.byCode(code), classFile, iter);
            commands.add(command);
        }
        calculateOffset(commands);
        return commands;
    }

    private static AbstractCommand _handle(Command command, ClassFile classFile, CommandIterator iter) {
        switch (command) {
            case NEW: {
                int operand1 = iter.next2CharAsInt();
                int operand2 = iter.next2CharAsInt();
                return new NewObjectCommand(classFile, command.getCode(), operand1, operand2);
            }
            case INVOKESPECIAL: {
                int operand1 = iter.next2CharAsInt();
                int operand2 = iter.next2CharAsInt();
                return new InvokeSpecialCommand(classFile, command.getCode(), operand1, operand2);
            }
            case INVOKEVIRTUAL: {
                int operand1 = iter.next2CharAsInt();
                int operand2 = iter.next2CharAsInt();
                return new InvokeVirtualCommand(classFile, command.getCode(), operand1, operand2);
            }
            case GETFIELD: {
                int operand1 = iter.next2CharAsInt();
                int operand2 = iter.next2CharAsInt();
                return new GetFieldCommand(classFile, command.getCode(), operand1, operand2);
            }
            case PUTFIELD: {
                int operand1 = iter.next2CharAsInt();
                int operand2 = iter.next2CharAsInt();
                return new PutFieldCommand(classFile, command.getCode(), operand1, operand2);
            }
            case GETSTATIC: {
                int operand1 = iter.next2CharAsInt();
                int operand2 = iter.next2CharAsInt();
                return new GetStaticFieldCommand(classFile, command.getCode(), operand1, operand2);
            }
            case BIPUSH: {
                int operand = iter.next2CharAsInt();
                return new BiPushCommand(classFile, command.getCode(), operand);
            }
            case LDC: {
                int operand = iter.next2CharAsInt();
                return new LdcCommand(classFile, command.getCode(), operand);
            }
            case ALOAD_0:
                return new ALoadNCommand(classFile, command.getCode(), 0);
            case ALOAD_1:
                return new ALoadNCommand(classFile, command.getCode(), 1);
            case ALOAD_2:
                return new ALoadNCommand(classFile, command.getCode(), 2);
            case ALOAD_3:
                return new ALoadNCommand(classFile, command.getCode(), 3);
            case ILOAD_0:
                return new ILoadNCommand(classFile, command.getCode(), 0);
            case ILOAD_1:
                return new ILoadNCommand(classFile, command.getCode(), 0);
            case ILOAD_2:
                return new ILoadNCommand(classFile, command.getCode(), 0);
            case ILOAD_3:
                return new ILoadNCommand(classFile, command.getCode(), 0);
            case RETURN:
                return new ReturnCommand(classFile, command.getCode());
            case DUP:
                return new DupCommand(classFile, command.getCode());
            case ASTORE_0:
                return new AStoreNCommand(classFile, command.getCode(), 0);
            case ASTORE_1:
                return new AStoreNCommand(classFile, command.getCode(), 1);
            case ASTORE_2:
                return new AStoreNCommand(classFile, command.getCode(), 2);
            case ASTORE_3:
                return new AStoreNCommand(classFile, command.getCode(), 3);
            case LLOAD_0:
                return new LLoadNCommand(classFile, command.getCode(), 0);
            case LLOAD_1:
                return new LLoadNCommand(classFile, command.getCode(), 1);
            case LLOAD_2:
                return new LLoadNCommand(classFile, command.getCode(), 2);
            case LLOAD_3:
                return new LLoadNCommand(classFile, command.getCode(), 3);
            case LSTORE_0:
                return new LStoreNCommand(classFile, command.getCode(), 0);
            case LSTORE_1:
                return new LStoreNCommand(classFile, command.getCode(), 1);
            case LSTORE_2:
                return new LStoreNCommand(classFile, command.getCode(), 2);
            case LSTORE_3:
                return new LStoreNCommand(classFile, command.getCode(), 3);
            case LDC2_W:{
                int operand1 = iter.next2CharAsInt();
                int operand2 = iter.next2CharAsInt();
                return new LDC2WCommand(classFile, command.getCode(), operand1, operand2);
            }
            case ACONST_NULL:
            case LSTORE:
            case ILOAD:
            case FLOAD_3:
            case FLOAD_2:
            case IF_ICMP_GE:
            case IF_ICMPLE:
            case GOTO:
            case IRETURN:
            case FRETURN:
            case ICONST_0:
            case ICONST_1:
            case ISTORE_1:
            case ISTORE_2:
            case IADD:
            case IINC:
            default:
                throw new RuntimeException("当前虚拟机还不支持该指令, " + command.getCode());
        }
    }

    private static void calculateOffset(List<AbstractCommand> cmds) {
        int offset = 0;
        for (AbstractCommand cmd : cmds) {
            cmd.setOffset(offset);
            offset += cmd.getCommandLength();
        }
    }

    private static class CommandIterator {
        String codes = null;
        int pos = 0;

        CommandIterator(String codes) {
            this.codes = codes;
        }

        public boolean hasNext() {
            return pos < this.codes.length();
        }

        public String next2CharAsString() {
            String result = codes.substring(pos, pos + 2);
            pos += 2;
            return result;
        }

        public int next2CharAsInt() {
            String s = this.next2CharAsString();
            return Integer.valueOf(s, 16);
        }
    }
}
