package com.worker.biz.service.worker;

import com.worker.client.request.worker.WorkerEnableRequest;
import com.worker.client.request.worker.WorkerPageRequest;
import com.worker.client.request.worker.WorkerRequest;
import com.worker.client.response.worker.WorkerInfoDTO;
import com.worker.client.response.worker.WorkerPageDTO;
import com.worker.client.response.worker.WorkerRoleDTO;
import com.worker.common.base.object.BasePage;
import com.worker.common.base.object.Result;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 工人信息Service接口
 *
 * @date: 2023/11/10 10:00
 */
public interface WorkerInfoService {

    /**
     * 工人信息分页查询
     *
     * @param request 分页请求
     * @return 分页数据
     */
    Result<BasePage<WorkerPageDTO>> pageWorkerInfo(WorkerPageRequest request);

    /**
     * 获取员工角色列表
     *
     * @return 员工角色列表
     */
    Result<List<WorkerRoleDTO>> getWorkerRoleList();

    /**
     * 新增工人信息
     *
     * @param request 工人信息请求
     * @return 是否成功
     */
    Result<Boolean> addWorkerInfo(WorkerRequest request);

    /**
     * 编辑工人信息
     *
     * @param request 工人信息请求
     * @return 是否成功
     */
    Result<Boolean> editWorkerInfo(WorkerRequest request);

    /**
     * 根据ID查询工人信息
     *
     * @param id 工人ID
     * @return 工人信息
     */
    Result<WorkerInfoDTO> queryWorkerInfoById(Long id);

    /**
     * 删除工人信息
     *
     * @param id 工人ID
     * @return 是否成功
     */
    Result<Boolean> deleteWorkerInfo(Long id);

    /**
     * 启用/禁用工人信息
     *
     * @param request 启用/禁用请求
     * @return 是否成功
     */
    Result<Boolean> enableWorkerInfo(WorkerEnableRequest request);

    /**
     * 下载工人信息导入模板
     *
     * @param response HTTP响应
     */
    void downloadImportTemplate(HttpServletResponse response);

    /**
     * 批量导入工人信息
     *
     * @param file Excel文件
     * @return 导入结果
     */
    Result<Boolean> importWorkerInfo(MultipartFile file);
}
