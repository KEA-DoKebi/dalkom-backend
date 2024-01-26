import xml.etree.ElementTree as ET
import pandas as pd
import glob

def parse_junit_xmls(file_path):
    tree = ET.parse(file_path)
    root = tree.getroot()

    total_tests = 0
    passed_tests = 0
    failure_details = []

    for testcase in root.iter('testcase'):
        total_tests += 1
        class_name = testcase.attrib['classname']
        method_name = testcase.attrib['name']
        status = 'PASSED'

        for child in testcase:
            if child.tag == 'failure':
                status = 'FAILED'
                failure_details.append([class_name, method_name, child.attrib['message']])

        if status == 'PASSED':
            passed_tests += 1

    success_rate = round((passed_tests / total_tests) * 100, 2) if total_tests > 0 else 0

    return total_tests, passed_tests, success_rate, failure_details

def generate_markdown_table(total_tests, passed_tests, success_rate, failure_details, output_file):
    summary = f"Total Tests: {total_tests}\nPassed Tests: {passed_tests}\nSuccess Rate: {success_rate}%\n\n"

    if failure_details:
        df = pd.DataFrame(failure_details, columns=['Class', 'Method', 'Reason'])
        markdown_table = df.to_markdown(index=False)
    else:
        markdown_table = "No failures found."

    with open(output_file, 'w') as file:
        file.write(summary)
        file.write(markdown_table)

# JUnit 결과 XML 파일들을 찾는 경로
junit_xml_files_path = './build/test-results/test/TEST-*.xml'

# 테스트 케이스 파싱
test_cases = parse_junit_xmls(glob.glob(junit_xml_files_path))

# Markdown 파일 생성
generate_markdown_table(test_cases, 'test_list.md')
