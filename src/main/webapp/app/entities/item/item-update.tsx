import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IClaim } from 'app/shared/model/claim.model';
import { getEntities as getClaims } from 'app/entities/claim/claim.reducer';
import { getEntity, updateEntity, createEntity, reset } from './item.reducer';
import { IItem } from 'app/shared/model/item.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IItemUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ItemUpdate = (props: IItemUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { itemEntity, claims, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/item');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getClaims();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.servicedDate = convertDateTimeToServer(values.servicedDate);
    values.servicedDateStart = convertDateTimeToServer(values.servicedDateStart);
    values.servicedDateEnd = convertDateTimeToServer(values.servicedDateEnd);

    if (errors.length === 0) {
      const entity = {
        ...itemEntity,
        ...values,
        claim: claims.find(it => it.id.toString() === values.claimId.toString()),
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
          <h2 id="hcpNphiesPortalApp.item.home.createOrEditLabel" data-cy="ItemCreateUpdateHeading">
            <Translate contentKey="hcpNphiesPortalApp.item.home.createOrEditLabel">Create or edit a Item</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : itemEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="item-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="item-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="sequenceLabel" for="item-sequence">
                  <Translate contentKey="hcpNphiesPortalApp.item.sequence">Sequence</Translate>
                </Label>
                <AvField
                  id="item-sequence"
                  data-cy="sequence"
                  type="string"
                  className="form-control"
                  name="sequence"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup check>
                <Label id="isPackageLabel">
                  <AvInput id="item-isPackage" data-cy="isPackage" type="checkbox" className="form-check-input" name="isPackage" />
                  <Translate contentKey="hcpNphiesPortalApp.item.isPackage">Is Package</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="taxLabel" for="item-tax">
                  <Translate contentKey="hcpNphiesPortalApp.item.tax">Tax</Translate>
                </Label>
                <AvField id="item-tax" data-cy="tax" type="text" name="tax" />
              </AvGroup>
              <AvGroup>
                <Label id="payerShareLabel" for="item-payerShare">
                  <Translate contentKey="hcpNphiesPortalApp.item.payerShare">Payer Share</Translate>
                </Label>
                <AvField id="item-payerShare" data-cy="payerShare" type="text" name="payerShare" />
              </AvGroup>
              <AvGroup>
                <Label id="patientShareLabel" for="item-patientShare">
                  <Translate contentKey="hcpNphiesPortalApp.item.patientShare">Patient Share</Translate>
                </Label>
                <AvField
                  id="item-patientShare"
                  data-cy="patientShare"
                  type="text"
                  name="patientShare"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="careTeamSequenceLabel" for="item-careTeamSequence">
                  <Translate contentKey="hcpNphiesPortalApp.item.careTeamSequence">Care Team Sequence</Translate>
                </Label>
                <AvField
                  id="item-careTeamSequence"
                  data-cy="careTeamSequence"
                  type="string"
                  className="form-control"
                  name="careTeamSequence"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="transportationSRCALabel" for="item-transportationSRCA">
                  <Translate contentKey="hcpNphiesPortalApp.item.transportationSRCA">Transportation SRCA</Translate>
                </Label>
                <AvField id="item-transportationSRCA" data-cy="transportationSRCA" type="text" name="transportationSRCA" />
              </AvGroup>
              <AvGroup>
                <Label id="imagingLabel" for="item-imaging">
                  <Translate contentKey="hcpNphiesPortalApp.item.imaging">Imaging</Translate>
                </Label>
                <AvField id="item-imaging" data-cy="imaging" type="text" name="imaging" />
              </AvGroup>
              <AvGroup>
                <Label id="laboratoryLabel" for="item-laboratory">
                  <Translate contentKey="hcpNphiesPortalApp.item.laboratory">Laboratory</Translate>
                </Label>
                <AvField id="item-laboratory" data-cy="laboratory" type="text" name="laboratory" />
              </AvGroup>
              <AvGroup>
                <Label id="medicalDeviceLabel" for="item-medicalDevice">
                  <Translate contentKey="hcpNphiesPortalApp.item.medicalDevice">Medical Device</Translate>
                </Label>
                <AvField id="item-medicalDevice" data-cy="medicalDevice" type="text" name="medicalDevice" />
              </AvGroup>
              <AvGroup>
                <Label id="oralHealthIPLabel" for="item-oralHealthIP">
                  <Translate contentKey="hcpNphiesPortalApp.item.oralHealthIP">Oral Health IP</Translate>
                </Label>
                <AvField id="item-oralHealthIP" data-cy="oralHealthIP" type="text" name="oralHealthIP" />
              </AvGroup>
              <AvGroup>
                <Label id="oralHealthOPLabel" for="item-oralHealthOP">
                  <Translate contentKey="hcpNphiesPortalApp.item.oralHealthOP">Oral Health OP</Translate>
                </Label>
                <AvField id="item-oralHealthOP" data-cy="oralHealthOP" type="text" name="oralHealthOP" />
              </AvGroup>
              <AvGroup>
                <Label id="procedureLabel" for="item-procedure">
                  <Translate contentKey="hcpNphiesPortalApp.item.procedure">Procedure</Translate>
                </Label>
                <AvField id="item-procedure" data-cy="procedure" type="text" name="procedure" />
              </AvGroup>
              <AvGroup>
                <Label id="servicesLabel" for="item-services">
                  <Translate contentKey="hcpNphiesPortalApp.item.services">Services</Translate>
                </Label>
                <AvField id="item-services" data-cy="services" type="text" name="services" />
              </AvGroup>
              <AvGroup>
                <Label id="medicationCodeLabel" for="item-medicationCode">
                  <Translate contentKey="hcpNphiesPortalApp.item.medicationCode">Medication Code</Translate>
                </Label>
                <AvField id="item-medicationCode" data-cy="medicationCode" type="text" name="medicationCode" />
              </AvGroup>
              <AvGroup>
                <Label id="servicedDateLabel" for="item-servicedDate">
                  <Translate contentKey="hcpNphiesPortalApp.item.servicedDate">Serviced Date</Translate>
                </Label>
                <AvInput
                  id="item-servicedDate"
                  data-cy="servicedDate"
                  type="datetime-local"
                  className="form-control"
                  name="servicedDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.itemEntity.servicedDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="servicedDateStartLabel" for="item-servicedDateStart">
                  <Translate contentKey="hcpNphiesPortalApp.item.servicedDateStart">Serviced Date Start</Translate>
                </Label>
                <AvInput
                  id="item-servicedDateStart"
                  data-cy="servicedDateStart"
                  type="datetime-local"
                  className="form-control"
                  name="servicedDateStart"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.itemEntity.servicedDateStart)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="servicedDateEndLabel" for="item-servicedDateEnd">
                  <Translate contentKey="hcpNphiesPortalApp.item.servicedDateEnd">Serviced Date End</Translate>
                </Label>
                <AvInput
                  id="item-servicedDateEnd"
                  data-cy="servicedDateEnd"
                  type="datetime-local"
                  className="form-control"
                  name="servicedDateEnd"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.itemEntity.servicedDateEnd)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="quantityLabel" for="item-quantity">
                  <Translate contentKey="hcpNphiesPortalApp.item.quantity">Quantity</Translate>
                </Label>
                <AvField
                  id="item-quantity"
                  data-cy="quantity"
                  type="string"
                  className="form-control"
                  name="quantity"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="unitPriceLabel" for="item-unitPrice">
                  <Translate contentKey="hcpNphiesPortalApp.item.unitPrice">Unit Price</Translate>
                </Label>
                <AvField
                  id="item-unitPrice"
                  data-cy="unitPrice"
                  type="string"
                  className="form-control"
                  name="unitPrice"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="factorLabel" for="item-factor">
                  <Translate contentKey="hcpNphiesPortalApp.item.factor">Factor</Translate>
                </Label>
                <AvField id="item-factor" data-cy="factor" type="text" name="factor" />
              </AvGroup>
              <AvGroup>
                <Label id="bodySiteLabel" for="item-bodySite">
                  <Translate contentKey="hcpNphiesPortalApp.item.bodySite">Body Site</Translate>
                </Label>
                <AvInput
                  id="item-bodySite"
                  data-cy="bodySite"
                  type="select"
                  className="form-control"
                  name="bodySite"
                  value={(!isNew && itemEntity.bodySite) || 'E1'}
                >
                  <option value="E1">{translate('hcpNphiesPortalApp.BodySiteEnum.E1')}</option>
                  <option value="E2">{translate('hcpNphiesPortalApp.BodySiteEnum.E2')}</option>
                  <option value="E3">{translate('hcpNphiesPortalApp.BodySiteEnum.E3')}</option>
                  <option value="E4">{translate('hcpNphiesPortalApp.BodySiteEnum.E4')}</option>
                  <option value="F1">{translate('hcpNphiesPortalApp.BodySiteEnum.F1')}</option>
                  <option value="F2">{translate('hcpNphiesPortalApp.BodySiteEnum.F2')}</option>
                  <option value="F3">{translate('hcpNphiesPortalApp.BodySiteEnum.F3')}</option>
                  <option value="F4">{translate('hcpNphiesPortalApp.BodySiteEnum.F4')}</option>
                  <option value="F5">{translate('hcpNphiesPortalApp.BodySiteEnum.F5')}</option>
                  <option value="F6">{translate('hcpNphiesPortalApp.BodySiteEnum.F6')}</option>
                  <option value="F7">{translate('hcpNphiesPortalApp.BodySiteEnum.F7')}</option>
                  <option value="F8">{translate('hcpNphiesPortalApp.BodySiteEnum.F8')}</option>
                  <option value="F9">{translate('hcpNphiesPortalApp.BodySiteEnum.F9')}</option>
                  <option value="FA">{translate('hcpNphiesPortalApp.BodySiteEnum.FA')}</option>
                  <option value="LC">{translate('hcpNphiesPortalApp.BodySiteEnum.LC')}</option>
                  <option value="LD">{translate('hcpNphiesPortalApp.BodySiteEnum.LD')}</option>
                  <option value="LM">{translate('hcpNphiesPortalApp.BodySiteEnum.LM')}</option>
                  <option value="LT">{translate('hcpNphiesPortalApp.BodySiteEnum.LT')}</option>
                  <option value="RC">{translate('hcpNphiesPortalApp.BodySiteEnum.RC')}</option>
                  <option value="RI">{translate('hcpNphiesPortalApp.BodySiteEnum.RI')}</option>
                  <option value="RT">{translate('hcpNphiesPortalApp.BodySiteEnum.RT')}</option>
                  <option value="T1">{translate('hcpNphiesPortalApp.BodySiteEnum.T1')}</option>
                  <option value="T2">{translate('hcpNphiesPortalApp.BodySiteEnum.T2')}</option>
                  <option value="T3">{translate('hcpNphiesPortalApp.BodySiteEnum.T3')}</option>
                  <option value="T4">{translate('hcpNphiesPortalApp.BodySiteEnum.T4')}</option>
                  <option value="T5">{translate('hcpNphiesPortalApp.BodySiteEnum.T5')}</option>
                  <option value="T6">{translate('hcpNphiesPortalApp.BodySiteEnum.T6')}</option>
                  <option value="T7">{translate('hcpNphiesPortalApp.BodySiteEnum.T7')}</option>
                  <option value="T8">{translate('hcpNphiesPortalApp.BodySiteEnum.T8')}</option>
                  <option value="T9">{translate('hcpNphiesPortalApp.BodySiteEnum.T9')}</option>
                  <option value="TA">{translate('hcpNphiesPortalApp.BodySiteEnum.TA')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="subSiteLabel" for="item-subSite">
                  <Translate contentKey="hcpNphiesPortalApp.item.subSite">Sub Site</Translate>
                </Label>
                <AvInput
                  id="item-subSite"
                  data-cy="subSite"
                  type="select"
                  className="form-control"
                  name="subSite"
                  value={(!isNew && itemEntity.subSite) || 'R'}
                >
                  <option value="R">{translate('hcpNphiesPortalApp.SubSiteEnum.R')}</option>
                  <option value="L">{translate('hcpNphiesPortalApp.SubSiteEnum.L')}</option>
                  <option value="U">{translate('hcpNphiesPortalApp.SubSiteEnum.U')}</option>
                  <option value="D">{translate('hcpNphiesPortalApp.SubSiteEnum.D')}</option>
                  <option value="A">{translate('hcpNphiesPortalApp.SubSiteEnum.A')}</option>
                  <option value="P">{translate('hcpNphiesPortalApp.SubSiteEnum.P')}</option>
                  <option value="I">{translate('hcpNphiesPortalApp.SubSiteEnum.I')}</option>
                  <option value="E">{translate('hcpNphiesPortalApp.SubSiteEnum.E')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="item-claim">
                  <Translate contentKey="hcpNphiesPortalApp.item.claim">Claim</Translate>
                </Label>
                <AvInput id="item-claim" data-cy="claim" type="select" className="form-control" name="claimId">
                  <option value="" key="0" />
                  {claims
                    ? claims.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/item" replace color="info">
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
  claims: storeState.claim.entities,
  itemEntity: storeState.item.entity,
  loading: storeState.item.loading,
  updating: storeState.item.updating,
  updateSuccess: storeState.item.updateSuccess,
});

const mapDispatchToProps = {
  getClaims,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ItemUpdate);
