import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IPractitionerRole } from 'app/shared/model/practitioner-role.model';
import { getEntities as getPractitionerRoles } from 'app/entities/practitioner-role/practitioner-role.reducer';
import { getEntity, updateEntity, createEntity, reset } from './list-role-code-enum.reducer';
import { IListRoleCodeEnum } from 'app/shared/model/list-role-code-enum.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ListRoleCodeEnumUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const practitionerRoles = useAppSelector(state => state.practitionerRole.entities);
  const listRoleCodeEnumEntity = useAppSelector(state => state.listRoleCodeEnum.entity);
  const loading = useAppSelector(state => state.listRoleCodeEnum.loading);
  const updating = useAppSelector(state => state.listRoleCodeEnum.updating);
  const updateSuccess = useAppSelector(state => state.listRoleCodeEnum.updateSuccess);

  const handleClose = () => {
    props.history.push('/list-role-code-enum');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getPractitionerRoles({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...listRoleCodeEnumEntity,
      ...values,
      practitionerRole: practitionerRoles.find(it => it.id.toString() === values.practitionerRoleId.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...listRoleCodeEnumEntity,
          r: 'Doctor',
          practitionerRoleId: listRoleCodeEnumEntity?.practitionerRole?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="hcpNphiesPortalApp.listRoleCodeEnum.home.createOrEditLabel" data-cy="ListRoleCodeEnumCreateUpdateHeading">
            <Translate contentKey="hcpNphiesPortalApp.listRoleCodeEnum.home.createOrEditLabel">Create or edit a ListRoleCodeEnum</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="list-role-code-enum-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('hcpNphiesPortalApp.listRoleCodeEnum.r')}
                id="list-role-code-enum-r"
                name="r"
                data-cy="r"
                type="select"
              >
                <option value="Doctor">{translate('hcpNphiesPortalApp.RoleCodeEnum.Doctor')}</option>
                <option value="Nurse">{translate('hcpNphiesPortalApp.RoleCodeEnum.Nurse')}</option>
                <option value="Pharmacist">{translate('hcpNphiesPortalApp.RoleCodeEnum.Pharmacist')}</option>
                <option value="Researcher">{translate('hcpNphiesPortalApp.RoleCodeEnum.Researcher')}</option>
                <option value="Teacher">{translate('hcpNphiesPortalApp.RoleCodeEnum.Teacher')}</option>
                <option value="Dentist">{translate('hcpNphiesPortalApp.RoleCodeEnum.Dentist')}</option>
                <option value="Physio">{translate('hcpNphiesPortalApp.RoleCodeEnum.Physio')}</option>
                <option value="Speech">{translate('hcpNphiesPortalApp.RoleCodeEnum.Speech')}</option>
                <option value="Ict">{translate('hcpNphiesPortalApp.RoleCodeEnum.Ict')}</option>
              </ValidatedField>
              <ValidatedField
                id="list-role-code-enum-practitionerRole"
                name="practitionerRoleId"
                data-cy="practitionerRole"
                label={translate('hcpNphiesPortalApp.listRoleCodeEnum.practitionerRole')}
                type="select"
              >
                <option value="" key="0" />
                {practitionerRoles
                  ? practitionerRoles.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/list-role-code-enum" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default ListRoleCodeEnumUpdate;
