import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IOrganization } from 'app/shared/model/organization.model';
import { getEntities as getOrganizations } from 'app/entities/organization/organization.reducer';
import { getEntity, updateEntity, createEntity, reset } from './hospitalization.reducer';
import { IHospitalization } from 'app/shared/model/hospitalization.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IHospitalizationUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const HospitalizationUpdate = (props: IHospitalizationUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { hospitalizationEntity, organizations, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/hospitalization');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getOrganizations();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...hospitalizationEntity,
        ...values,
        origin: organizations.find(it => it.id.toString() === values.originId.toString()),
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
          <h2 id="hcpNphiesPortalApp.hospitalization.home.createOrEditLabel" data-cy="HospitalizationCreateUpdateHeading">
            <Translate contentKey="hcpNphiesPortalApp.hospitalization.home.createOrEditLabel">Create or edit a Hospitalization</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : hospitalizationEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="hospitalization-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="hospitalization-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="admitSourceLabel" for="hospitalization-admitSource">
                  <Translate contentKey="hcpNphiesPortalApp.hospitalization.admitSource">Admit Source</Translate>
                </Label>
                <AvInput
                  id="hospitalization-admitSource"
                  data-cy="admitSource"
                  type="select"
                  className="form-control"
                  name="admitSource"
                  value={(!isNew && hospitalizationEntity.admitSource) || 'IA'}
                >
                  <option value="IA">{translate('hcpNphiesPortalApp.AdmitSourceEnum.IA')}</option>
                  <option value="EER">{translate('hcpNphiesPortalApp.AdmitSourceEnum.EER')}</option>
                  <option value="EOP">{translate('hcpNphiesPortalApp.AdmitSourceEnum.EOP')}</option>
                  <option value="EGPHC">{translate('hcpNphiesPortalApp.AdmitSourceEnum.EGPHC')}</option>
                  <option value="EGGH">{translate('hcpNphiesPortalApp.AdmitSourceEnum.EGGH')}</option>
                  <option value="EPPHC">{translate('hcpNphiesPortalApp.AdmitSourceEnum.EPPHC')}</option>
                  <option value="EPH">{translate('hcpNphiesPortalApp.AdmitSourceEnum.EPH')}</option>
                  <option value="EIC">{translate('hcpNphiesPortalApp.AdmitSourceEnum.EIC')}</option>
                  <option value="EWGS">{translate('hcpNphiesPortalApp.AdmitSourceEnum.EWGS')}</option>
                  <option value="EWSS">{translate('hcpNphiesPortalApp.AdmitSourceEnum.EWSS')}</option>
                  <option value="EWIS">{translate('hcpNphiesPortalApp.AdmitSourceEnum.EWIS')}</option>
                  <option value="EMBA">{translate('hcpNphiesPortalApp.AdmitSourceEnum.EMBA')}</option>
                  <option value="PMBA">{translate('hcpNphiesPortalApp.AdmitSourceEnum.PMBA')}</option>
                  <option value="Others">{translate('hcpNphiesPortalApp.AdmitSourceEnum.Others')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="reAdmissionLabel" for="hospitalization-reAdmission">
                  <Translate contentKey="hcpNphiesPortalApp.hospitalization.reAdmission">Re Admission</Translate>
                </Label>
                <AvInput
                  id="hospitalization-reAdmission"
                  data-cy="reAdmission"
                  type="select"
                  className="form-control"
                  name="reAdmission"
                  value={(!isNew && hospitalizationEntity.reAdmission) || 'R'}
                >
                  <option value="R">{translate('hcpNphiesPortalApp.ReAdmissionEnum.R')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="dischargeDispositionLabel" for="hospitalization-dischargeDisposition">
                  <Translate contentKey="hcpNphiesPortalApp.hospitalization.dischargeDisposition">Discharge Disposition</Translate>
                </Label>
                <AvInput
                  id="hospitalization-dischargeDisposition"
                  data-cy="dischargeDisposition"
                  type="select"
                  className="form-control"
                  name="dischargeDisposition"
                  value={(!isNew && hospitalizationEntity.dischargeDisposition) || 'Home'}
                >
                  <option value="Home">{translate('hcpNphiesPortalApp.DischargeDispositionEnum.Home')}</option>
                  <option value="DASHalt_home">{translate('hcpNphiesPortalApp.DischargeDispositionEnum.DASHalt_home')}</option>
                  <option value="DASHother_hcf">{translate('hcpNphiesPortalApp.DischargeDispositionEnum.DASHother_hcf')}</option>
                  <option value="Hosp">{translate('hcpNphiesPortalApp.DischargeDispositionEnum.Hosp')}</option>
                  <option value="DASHlong">{translate('hcpNphiesPortalApp.DischargeDispositionEnum.DASHlong')}</option>
                  <option value="Aadvice">{translate('hcpNphiesPortalApp.DischargeDispositionEnum.Aadvice')}</option>
                  <option value="Exp">{translate('hcpNphiesPortalApp.DischargeDispositionEnum.Exp')}</option>
                  <option value="Psy">{translate('hcpNphiesPortalApp.DischargeDispositionEnum.Psy')}</option>
                  <option value="Rehab">{translate('hcpNphiesPortalApp.DischargeDispositionEnum.Rehab')}</option>
                  <option value="Snf">{translate('hcpNphiesPortalApp.DischargeDispositionEnum.Snf')}</option>
                  <option value="Oth">{translate('hcpNphiesPortalApp.DischargeDispositionEnum.Oth')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="hospitalization-origin">
                  <Translate contentKey="hcpNphiesPortalApp.hospitalization.origin">Origin</Translate>
                </Label>
                <AvInput id="hospitalization-origin" data-cy="origin" type="select" className="form-control" name="originId">
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
              <Button tag={Link} id="cancel-save" to="/hospitalization" replace color="info">
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
  organizations: storeState.organization.entities,
  hospitalizationEntity: storeState.hospitalization.entity,
  loading: storeState.hospitalization.loading,
  updating: storeState.hospitalization.updating,
  updateSuccess: storeState.hospitalization.updateSuccess,
});

const mapDispatchToProps = {
  getOrganizations,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(HospitalizationUpdate);
