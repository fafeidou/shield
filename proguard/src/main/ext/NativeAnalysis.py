import getopt
import sys
from typing import List, Optional
import setproctitle
from elftools.elf.elffile import ELFFile
from loguru import logger

def read_symbols(input_file = None, output_file = None):
    with open(input_file, 'rb') as f:
        elf_file = ELFFile(f)
        if not elf_file.has_dwarf_info():
            logger.info('No DWARF info found in the file.')
            return 100
        dwarf_info = elf_file.get_dwarf_info()
        address_to_source = { }
        for CU in dwarf_info.iter_CUs():
            line_program = dwarf_info.line_program_for_CU(CU)
            if line_program is None:
                continue
            for entry in line_program.get_entries():
                if entry.state:
                    file_index = entry.state.file - 1
                    file_entry = line_program['file_entry'][file_index]
                    file_name = file_entry.name.decode()
                    line_number = entry.state.line
                    address_to_source[entry.state.address] = file_name + ':' + str(line_number)
    address_to_source = dict(sorted(address_to_source.items()))
    output_symbols(address_to_source, output_file)
    return 0

def output_symbols(symbols = None, output_file = None):
    with open(output_file, 'w') as writer:
        for address, source_info in symbols.items():
            line = f'''{address}$$${source_info}\n'''
            writer.write(line)

if __name__ == '__main__':
    a_input_file = ""
    a_output_file = ""
    read_symbols(a_input_file, a_output_file)