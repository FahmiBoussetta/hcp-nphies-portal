import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IPractitionerRole } from 'app/shared/model/practitioner-role.model';
import { getEntities as getPractitionerRoles } from 'app/entities/practitioner-role/practitioner-role.reducer';
import { getEntity, updateEntity, createEntity, reset } from './list-role-code-enum.reducer';
import { IListRoleCodeEnum } from 'app/shared/model/list-role-code-enum.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IListRoleCodeEnumUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ListRoleCodeEnumUpdate = (props: IListRoleCodeEnumUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { listRoleCodeEnumEntity, practitionerRoles, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/list-role-code-enum');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getPractitionerRoles();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...listRoleCodeEnumEntity,
        ...values,
        practitionerRole: practitionerRoles.find(it => it.id.toString() === values.practitionerRoleId.toString()),
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
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
            <AvForm model={isNew ? {} : listRoleCodeEnumEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="list-role-code-enum-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="list-role-code-enum-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="rLabel" for="list-role-code-enum-r">
                  <Translate contentKey="hcpNphiesPortalApp.listRoleCodeEnum.r">R</Translate>
                </Label>
                <AvInput
                  id="list-role-code-enum-r"
                  data-cy="r"
                  type="select"
                  className="form-control"
                  name="r"
                  value={(!isNew && listRoleCodeEnumEntity.r) || 'Doctor'}
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
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="list-role-code-enum-practitionerRole">
                  <Translate contentKey="hcpNphiesPortalApp.listRoleCodeEnum.practitionerRole">Practitioner Role</Translate>
                </Label>
                <AvInput
                  id="list-role-code-enum-practitionerRole"
                  data-cy="practitionerRole"
                  type="select"
                  className="form-control"
                  name="practitionerRoleId"
                >
                  <option value="" key="0" />
                  {practitionerRoles
                    ? practitionerRoles.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/list-role-code-enum" replace color="info">
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
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  practitionerRoles: storeState.practitionerRole.entities,
  listRoleCodeEnumEntity: storeState.listRoleCodeEnum.entity,
  loading: storeState.listRoleCodeEnum.loading,
  updating: storeState.listRoleCodeEnum.updating,
  updateSuccess: storeState.listRoleCodeEnum.updateSuccess,
});

const mapDispatchToProps = {
  getPractitionerRoles,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ListRoleCodeEnumUpdate);
