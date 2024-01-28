import xml.etree.ElementTree as ET
import pandas as pd
import glob

def parse_junit_xml(file_path):
    tree = ET.parse(file_path)
    root = tree.getroot()

    total_tests = 0
    passed_tests = 0
    failed_tests = 0
    failure_details = []

    for testcase in root.iter('testcase'):
        total_tests += 1
        class_name = testcase.attrib['classname']
        method_name = testcase.attrib['name']
        time = float(testcase.attrib['time'])
        status = 'PASSED'

        for child in testcase:
            if child.tag == 'failure':
                status = 'FAILED'
                failed_tests += 1
                failure_message = child.attrib['message']
                failure_details.append([class_name, method_name, failure_message])

        if status == 'PASSED':
            passed_tests += 1

    success_rate = round((passed_tests / total_tests) * 100, 2)

    return total_tests, passed_tests, success_rate, failure_details

def generate_markdown_table(total_tests, passed_tests, success_rate, failure_details, output_file):
    with open(output_file, 'w') as file:
        file.write(f"Total Tests: {total_tests}\n")
        file.write(f"Passed Tests: {passed_tests}\n")
        file.write(f"Success Rate: {success_rate}%\n\n")
        
        if failed_tests > 0:
            df = pd.DataFrame(failure_details, columns=['Class', 'Method', 'Reason'])
            markdown_table = df.to_markdown(index=False)
            file.write(markdown_table)

# Write a python code that path of junit test result file
junit_xml_files_path = glob.glob('./build/test-results/test/TEST-*.xml')

# 테스트 케이스 파싱
total_tests, passed_tests, success_rate, failure_details = parse_junit_xml(junit_xml_files_path)

# Markdown 파일 생성
generate_markdown_table(total_tests, passed_tests, success_rate, failure_details, 'test_list.md')