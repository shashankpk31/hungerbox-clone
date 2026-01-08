import Input from '../../../../components/ui/Input';

const EmployeeFields = ({ onDataChange }) => (
  <>
    <Input label="Employee ID" name="employeeId" placeholder="EMP12345" onChange={onDataChange} required />
    <Input label="Company Name" name="companyName" placeholder="Tech Corp" onChange={onDataChange} required />
  </>
);
export default EmployeeFields;