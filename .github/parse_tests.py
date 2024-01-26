import xml.etree.ElementTree as ET
import pandas as pd
import glob

def parse_junit_xmls(file_paths):
    test_cases = []

    for file_path in file_paths:
        tree = ET.parse(file_path)
        root = tree.getroot()

        for testcase in root.iter('testcase'):
            class_name = testcase.attrib['classname']
            method_name = testcase.attrib['name']
            time = testcase.attrib['time']
            status = 'PASSED'

            for child in testcase:
                if child.tag == 'failure':
                    status = 'FAILED'

            test_cases.append([class_name, method_name, status, time])

    return test_cases

def generate_markdown_table(test_cases, output_file):
    df = pd.DataFrame(test_cases, columns=['Class', 'Method', 'Status', 'Time'])
    markdown_table = df.to_markdown(index=False)
    
    with open(output_file, 'w') as file:
        file.write(markdown_table)

# JUnit 결과 XML 파일들을 찾는 경로
junit_xml_files_path = './build/test-results/test/TEST-*.xml'

# 테스트 케이스 파싱
test_cases = parse_junit_xmls(glob.glob(junit_xml_files_path))

# Markdown 파일 생성
generate_markdown_table(test_cases, 'test_list.md')
