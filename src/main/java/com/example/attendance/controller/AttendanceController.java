package com.example.attendance.controller;

import com.example.attendance.entity.Attendance;
import com.example.attendance.repository.AttendanceRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class AttendanceController {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @GetMapping("/check")
    public String checkPage() {
        return "check";
    }

    @PostMapping("/check")
    public String doCheck(@RequestParam String studentId,
                          @RequestParam String studentName,
                          @RequestParam String courseId,
                          Model model) {

        LocalDateTime now = LocalDateTime.now();

        LocalDateTime courseTime = now.withHour(9)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);

        String status;

        if (now.isBefore(courseTime.minusMinutes(15))) {
            model.addAttribute("msg", "未到打卡时间，不能打卡");
            return "check";
        } else if (now.isAfter(courseTime.plusMinutes(30))) {
            status = "LATE";
        } else {
            status = "NORMAL";
        }

        Attendance attendance = new Attendance();
        attendance.setStudentId(studentId);
        attendance.setStudentName(studentName);
        attendance.setCourseId(courseId);
        attendance.setCheckInTime(now);
        attendance.setStatus(status);
        attendance.setCreateTime(now);

        attendanceRepository.save(attendance);

        model.addAttribute("msg", "打卡成功，状态：" + status);

        return "check";
    }

    @GetMapping("/attendance/list")
    public String list(@RequestParam(required = false) String courseId,
                       @RequestParam(required = false) String status,
                       @RequestParam(required = false) String timeRange,
                       Model model) {

        Specification<Attendance> spec = (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (courseId != null && !courseId.trim().isEmpty()) {
                predicates.add(cb.equal(root.get("courseId"), courseId));
            }

            if (status != null && !status.trim().isEmpty()) {
                predicates.add(cb.equal(root.get("status"), status));
            }

            if (timeRange != null && !timeRange.trim().isEmpty()) {
                LocalDateTime now = LocalDateTime.now();
                LocalDateTime start = null;

                if ("today".equals(timeRange)) {
                    start = now.toLocalDate().atStartOfDay();
                } else if ("week".equals(timeRange)) {
                    start = now.minusDays(7);
                } else if ("month".equals(timeRange)) {
                    start = now.minusMonths(1);
                }

                if (start != null) {
                    predicates.add(cb.between(root.get("checkInTime"), start, now));
                }
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        List<Attendance> records = attendanceRepository.findAll(
                spec,
                Sort.by(Sort.Direction.DESC, "checkInTime")
        );

        model.addAttribute("records", records);
        model.addAttribute("courseId", courseId);
        model.addAttribute("status", status);
        model.addAttribute("timeRange", timeRange);

        return "attendance-list";
    }

    @GetMapping("/attendance/export")
    public void export(HttpServletResponse response) throws IOException {

        List<Attendance> records = attendanceRepository.findAll(
                Sort.by(Sort.Direction.DESC, "checkInTime")
        );

        response.setContentType("text/csv;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=attendance.csv");

        PrintWriter writer = response.getWriter();

        // 防止 Excel 打开 CSV 中文乱码
        writer.write('\uFEFF');

        writer.println("ID,StudentId,StudentName,CourseId,CheckInTime,Status");

        for (Attendance record : records) {
            writer.println(
                    record.getId() + "," +
                            record.getStudentId() + "," +
                            record.getStudentName() + "," +
                            record.getCourseId() + "," +
                            record.getCheckInTime() + "," +
                            record.getStatus()
            );
        }

        writer.flush();
    }

    @GetMapping("/attendance/upload")
    public String uploadPage() {
        return "upload";
    }

    @PostMapping("/attendance/upload")
    public String uploadExcel(@RequestParam("file") MultipartFile file,
                              Model model) {

        if (file.isEmpty()) {
            model.addAttribute("msg", "请选择文件");
            return "upload";
        }

        long maxSize = 10 * 1024 * 1024;

        if (file.getSize() > maxSize) {
            model.addAttribute("msg", "文件过大，最大只能上传 10MB");
            return "upload";
        }

        String filename = file.getOriginalFilename();

        if (filename == null || !filename.endsWith(".xlsx")) {
            model.addAttribute("msg", "文件格式错误，只支持 .xlsx 文件");
            return "upload";
        }

        String defaultCourseId = "CS101";

        try (
                InputStream inputStream = file.getInputStream();
                Workbook workbook = new XSSFWorkbook(inputStream)
        ) {

            Sheet sheet = workbook.getSheetAt(0);

            int successCount = 0;
            int skipCount = 0;
            int errorCount = 0;

            LocalDateTime now = LocalDateTime.now();
            LocalDateTime dayStart = now.toLocalDate().atStartOfDay();
            LocalDateTime dayEnd = dayStart.plusDays(1).minusSeconds(1);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {

                Row row = sheet.getRow(i);

                if (row == null) {
                    errorCount++;
                    continue;
                }

                Cell studentIdCell = row.getCell(0);
                Cell studentNameCell = row.getCell(1);

                if (studentIdCell == null || studentNameCell == null) {
                    errorCount++;
                    continue;
                }

                String studentId = getCellValue(studentIdCell).trim();
                String studentName = getCellValue(studentNameCell).trim();

                if (studentId.isEmpty() || studentName.isEmpty()) {
                    errorCount++;
                    continue;
                }

                boolean exists = attendanceRepository.existsByStudentIdAndCourseIdAndCheckInTimeBetween(
                        studentId,
                        defaultCourseId,
                        dayStart,
                        dayEnd
                );

                if (exists) {
                    skipCount++;
                    continue;
                }

                Attendance attendance = new Attendance();

                attendance.setStudentId(studentId);
                attendance.setStudentName(studentName);
                attendance.setCourseId(defaultCourseId);
                attendance.setStatus("NORMAL");
                attendance.setCheckInTime(now);
                attendance.setCreateTime(now);

                attendanceRepository.save(attendance);

                successCount++;
            }

            model.addAttribute(
                    "msg",
                    "Excel导入完成：成功导入 " + successCount +
                            " 条，重复跳过 " + skipCount +
                            " 条，异常跳过 " + errorCount + " 条"
            );

        } catch (Exception e) {

            e.printStackTrace();

            model.addAttribute("msg", "Excel导入失败：" + e.getMessage());
        }

        return "upload";
    }
    @GetMapping("/attendance/statistics")
    public String statistics(Model model) {

        List<Attendance> records = attendanceRepository.findAll();

        long totalCount = records.size();

        long normalCount = records.stream()
                .filter(record -> "NORMAL".equals(record.getStatus()))
                .count();

        long lateCount = records.stream()
                .filter(record -> "LATE".equals(record.getStatus()))
                .count();

        double normalRate = 0.0;

        if (totalCount > 0) {
            normalRate = normalCount * 100.0 / totalCount;
        }

        LocalDateTime now = LocalDateTime.now();

        long weekCount = records.stream()
                .filter(record -> record.getCheckInTime() != null)
                .filter(record -> record.getCheckInTime().isAfter(now.minusDays(7)))
                .count();

        long monthCount = records.stream()
                .filter(record -> record.getCheckInTime() != null)
                .filter(record -> record.getCheckInTime().isAfter(now.minusMonths(1)))
                .count();

        Map<String, Long> courseCountMap = records.stream()
                .filter(record -> record.getCourseId() != null)
                .collect(Collectors.groupingBy(
                        Attendance::getCourseId,
                        Collectors.counting()
                ));

        model.addAttribute("totalCount", totalCount);
        model.addAttribute("normalCount", normalCount);
        model.addAttribute("lateCount", lateCount);
        model.addAttribute("normalRate", String.format("%.2f", normalRate));
        model.addAttribute("weekCount", weekCount);
        model.addAttribute("monthCount", monthCount);
        model.addAttribute("courseCountMap", courseCountMap);

        return "attendance-statistics";
    }

    private String getCellValue(Cell cell) {

        if (cell == null) {
            return "";
        }

        cell.setCellType(CellType.STRING);

        return cell.getStringCellValue();
    }
}