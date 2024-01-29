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
        status = 'PASSED'

        for child in testcase:
            if child.tag == 'failure':
                status = 'FAILED'
                failed_tests += 1
                failure_message = child.attrib.get('message', 'No message')  # 메시지가 없는 경우 대비
                failure_details.append([class_name, method_name, failure_message])

        if status == 'PASSED':
            passed_tests += 1

    return total_tests, passed_tests, failed_tests, failure_details

def generate_markdown_table(total_tests, passed_tests, failed_tests, failure_details, output_file):
    with open(output_file, 'w') as file:
        file.write(f"Total Tests: {total_tests}\n")
        file.write(f"Passed Tests: {passed_tests}\n")
        success_rate = round((passed_tests / total_tests) * 100, 2) if total_tests else 0
        file.write(f"Success Rate: {success_rate}%\n\n")
        
        if failed_tests > 0:
            df = pd.DataFrame(failure_details, columns=['Class', 'Method', 'Reason'])
            markdown_table = df.to_markdown(index=False)
            file.write(markdown_table)

# 파일 경로 검색
junit_xml_files_paths = glob.glob("./build/test-results/test/TEST-*.xml")

# 각 파일에 대해 테스트 결과 집계
total_tests = passed_tests = failed_tests = 0
failure_details = []

for file_path in junit_xml_files_paths:
    t_tests, p_tests, f_tests, f_details = parse_junit_xml(file_path)
    total_tests += t_tests
    passed_tests += p_tests
    failed_tests += f_tests
    failure_details.extend(f_details)

# Markdown 파일 생성
generate_markdown_table(total_tests, passed_tests, failed_tests, failure_details, 'test_results_summary.md')