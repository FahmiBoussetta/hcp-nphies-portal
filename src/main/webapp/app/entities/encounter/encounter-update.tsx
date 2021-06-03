import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IPatient } from 'app/shared/model/patient.model';
import { getEntities as getPatients } from 'app/entities/patient/patient.reducer';
import { IHospitalization } from 'app/shared/model/hospitalization.model';
import { getEntities as getHospitalizations } from 'app/entities/hospitalization/hospitalization.reducer';
import { IOrganization } from 'app/shared/model/organization.model';
import { getEntities as getOrganizations } from 'app/entities/organization/organization.reducer';
import { getEntity, updateEntity, createEntity, reset } from './encounter.reducer';
import { IEncounter } from 'app/shared/model/encounter.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IEncounterUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EncounterUpdate = (props: IEncounterUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { encounterEntity, patients, hospitalizations, organizations, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/encounter');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getPatients();
    props.getHospitalizations();
    props.getOrganizations();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.start = convertDateTimeToServer(values.start);
    values.end = convertDateTimeToServer(values.end);

    if (errors.length === 0) {
      const entity = {
        ...encounterEntity,
        ...values,
        subject: patients.find(it => it.id.toString() === values.subjectId.toString()),
        hospitalization: hospitalizations.find(it => it.id.toString() === values.hospitalizationId.toString()),
        serviceProvider: organizations.find(it => it.id.toString() === values.serviceProviderId.toString()),
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
          <h2 id="hcpNphiesPortalApp.encounter.home.createOrEditLabel" data-cy="EncounterCreateUpdateHeading">
            <Translate contentKey="hcpNphiesPortalApp.encounter.home.createOrEditLabel">Create or edit a Encounter</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : encounterEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="encounter-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="encounter-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="guidLabel" for="encounter-guid">
                  <Translate contentKey="hcpNphiesPortalApp.encounter.guid">Guid</Translate>
                </Label>
                <AvField id="encounter-guid" data-cy="guid" type="text" name="guid" />
              </AvGroup>
              <AvGroup>
                <Label id="forceIdLabel" for="encounter-forceId">
                  <Translate contentKey="hcpNphiesPortalApp.encounter.forceId">Force Id</Translate>
                </Label>
                <AvField id="encounter-forceId" data-cy="forceId" type="text" name="forceId" />
              </AvGroup>
              <AvGroup>
                <Label id="identifierLabel" for="encounter-identifier">
                  <Translate contentKey="hcpNphiesPortalApp.encounter.identifier">Identifier</Translate>
                </Label>
                <AvField id="encounter-identifier" data-cy="identifier" type="text" name="identifier" />
              </AvGroup>
              <AvGroup>
                <Label id="encounterClassLabel" for="encounter-encounterClass">
                  <Translate contentKey="hcpNphiesPortalApp.encounter.encounterClass">Encounter Class</Translate>
                </Label>
                <AvInput
                  id="encounter-encounterClass"
                  data-cy="encounterClass"
                  type="select"
                  className="form-control"
                  name="encounterClass"
                  value={(!isNew && encounterEntity.encounterClass) || 'AMB'}
                >
                  <option value="AMB">{translate('hcpNphiesPortalApp.EncounterClassEnum.AMB')}</option>
                  <option value="EMER">{translate('hcpNphiesPortalApp.EncounterClassEnum.EMER')}</option>
                  <option value="HH">{translate('hcpNphiesPortalApp.EncounterClassEnum.HH')}</option>
                  <option value="IMP">{translate('hcpNphiesPortalApp.EncounterClassEnum.IMP')}</option>
                  <option value="SS">{translate('hcpNphiesPortalApp.EncounterClassEnum.SS')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="startLabel" for="encounter-start">
                  <Translate contentKey="hcpNphiesPortalApp.encounter.start">Start</Translate>
                </Label>
                <AvInput
                  id="encounter-start"
                  data-cy="start"
                  type="datetime-local"
                  className="form-control"
                  name="start"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.encounterEntity.start)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="endLabel" for="encounter-end">
                  <Translate contentKey="hcpNphiesPortalApp.encounter.end">End</Translate>
                </Label>
                <AvInput
                  id="encounter-end"
                  data-cy="end"
                  type="datetime-local"
                  className="form-control"
                  name="end"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.encounterEntity.end)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="serviceTypeLabel" for="encounter-serviceType">
                  <Translate contentKey="hcpNphiesPortalApp.encounter.serviceType">Service Type</Translate>
                </Label>
                <AvInput
                  id="encounter-serviceType"
                  data-cy="serviceType"
                  type="select"
                  className="form-control"
                  name="serviceType"
                  value={(!isNew && encounterEntity.serviceType) || 'N237'}
                >
                  <option value="N237">{translate('hcpNphiesPortalApp.ServiceTypeEnum.N237')}</option>
                  <option value="N576">{translate('hcpNphiesPortalApp.ServiceTypeEnum.N576')}</option>
                  <option value="N356">{translate('hcpNphiesPortalApp.ServiceTypeEnum.N356')}</option>
                  <option value="N621">{translate('hcpNphiesPortalApp.ServiceTypeEnum.N621')}</option>
                  <option value="N179">{translate('hcpNphiesPortalApp.ServiceTypeEnum.N179')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="priorityLabel" for="encounter-priority">
                  <Translate contentKey="hcpNphiesPortalApp.encounter.priority">Priority</Translate>
                </Label>
                <AvInput
                  id="encounter-priority"
                  data-cy="priority"
                  type="select"
                  className="form-control"
                  name="priority"
                  value={(!isNew && encounterEntity.priority) || 'EM'}
                >
                  <option value="EM">{translate('hcpNphiesPortalApp.ActPriorityEnum.EM')}</option>
                  <option value="EL">{translate('hcpNphiesPortalApp.ActPriorityEnum.EL')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="encounter-subject">
                  <Translate contentKey="hcpNphiesPortalApp.encounter.subject">Subject</Translate>
                </Label>
                <AvInput id="encounter-subject" data-cy="subject" type="select" className="form-control" name="subjectId">
                  <option value="" key="0" />
                  {patients
                    ? patients.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="encounter-hospitalization">
                  <Translate contentKey="hcpNphiesPortalApp.encounter.hospitalization">Hospitalization</Translate>
                </Label>
                <AvInput
                  id="encounter-hospitalization"
                  data-cy="hospitalization"
                  type="select"
                  className="form-control"
                  name="hospitalizationId"
                >
                  <option value="" key="0" />
                  {hospitalizations
                    ? hospitalizations.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="encounter-serviceProvider">
                  <Translate contentKey="hcpNphiesPortalApp.encounter.serviceProvider">Service Provider</Translate>
                </Label>
                <AvInput
                  id="encounter-serviceProvider"
                  data-cy="serviceProvider"
                  type="select"
                  className="form-control"
                  name="serviceProviderId"
                >
                  <option value="" key="0" />
                  {organizations
                    ? organizations.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/encounter" replace color="info">
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
  patients: storeState.patient.entities,
  hospitalizations: storeState.hospitalization.entities,
  organizations: storeState.organization.entities,
  encounterEntity: storeState.encounter.entity,
  loading: storeState.encounter.loading,
  updating: storeState.encounter.updating,
  updateSuccess: storeState.encounter.updateSuccess,
});

const mapDispatchToProps = {
  getPatients,
  getHospitalizations,
  getOrganizations,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EncounterUpdate);
