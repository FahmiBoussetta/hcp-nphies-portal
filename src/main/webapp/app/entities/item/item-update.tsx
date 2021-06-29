import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IClaim } from 'app/shared/model/claim.model';
import { getEntities as getClaims } from 'app/entities/claim/claim.reducer';
import { getEntity, updateEntity, createEntity, reset } from './item.reducer';
import { IItem } from 'app/shared/model/item.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ItemUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const claims = useAppSelector(state => state.claim.entities);
  const itemEntity = useAppSelector(state => state.item.entity);
  const loading = useAppSelector(state => state.item.loading);
  const updating = useAppSelector(state => state.item.updating);
  const updateSuccess = useAppSelector(state => state.item.updateSuccess);

  const handleClose = () => {
    props.history.push('/item');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getClaims({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.servicedDate = convertDateTimeToServer(values.servicedDate);
    values.servicedDateStart = convertDateTimeToServer(values.servicedDateStart);
    values.servicedDateEnd = convertDateTimeToServer(values.servicedDateEnd);

    const entity = {
      ...itemEntity,
      ...values,
      claim: claims.find(it => it.id.toString() === values.claimId.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          servicedDate: displayDefaultDateTime(),
          servicedDateStart: displayDefaultDateTime(),
          servicedDateEnd: displayDefaultDateTime(),
        }
      : {
          ...itemEntity,
          servicedDate: convertDateTimeFromServer(itemEntity.servicedDate),
          servicedDateStart: convertDateTimeFromServer(itemEntity.servicedDateStart),
          servicedDateEnd: convertDateTimeFromServer(itemEntity.servicedDateEnd),
          bodySite: 'E1',
          subSite: 'R',
          claimId: itemEntity?.claim?.id,
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
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="item-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('hcpNphiesPortalApp.item.sequence')}
                id="item-sequence"
                name="sequence"
                data-cy="sequence"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.item.isPackage')}
                id="item-isPackage"
                name="isPackage"
                data-cy="isPackage"
                check
                type="checkbox"
              />
              <ValidatedField label={translate('hcpNphiesPortalApp.item.tax')} id="item-tax" name="tax" data-cy="tax" type="text" />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.item.payerShare')}
                id="item-payerShare"
                name="payerShare"
                data-cy="payerShare"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.item.patientShare')}
                id="item-patientShare"
                name="patientShare"
                data-cy="patientShare"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.item.careTeamSequence')}
                id="item-careTeamSequence"
                name="careTeamSequence"
                data-cy="careTeamSequence"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.item.transportationSRCA')}
                id="item-transportationSRCA"
                name="transportationSRCA"
                data-cy="transportationSRCA"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.item.imaging')}
                id="item-imaging"
                name="imaging"
                data-cy="imaging"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.item.laboratory')}
                id="item-laboratory"
                name="laboratory"
                data-cy="laboratory"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.item.medicalDevice')}
                id="item-medicalDevice"
                name="medicalDevice"
                data-cy="medicalDevice"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.item.oralHealthIP')}
                id="item-oralHealthIP"
                name="oralHealthIP"
                data-cy="oralHealthIP"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.item.oralHealthOP')}
                id="item-oralHealthOP"
                name="oralHealthOP"
                data-cy="oralHealthOP"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.item.procedure')}
                id="item-procedure"
                name="procedure"
                data-cy="procedure"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.item.services')}
                id="item-services"
                name="services"
                data-cy="services"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.item.medicationCode')}
                id="item-medicationCode"
                name="medicationCode"
                data-cy="medicationCode"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.item.servicedDate')}
                id="item-servicedDate"
                name="servicedDate"
                data-cy="servicedDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.item.servicedDateStart')}
                id="item-servicedDateStart"
                name="servicedDateStart"
                data-cy="servicedDateStart"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.item.servicedDateEnd')}
                id="item-servicedDateEnd"
                name="servicedDateEnd"
                data-cy="servicedDateEnd"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.item.quantity')}
                id="item-quantity"
                name="quantity"
                data-cy="quantity"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.item.unitPrice')}
                id="item-unitPrice"
                name="unitPrice"
                data-cy="unitPrice"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.item.factor')}
                id="item-factor"
                name="factor"
                data-cy="factor"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.item.bodySite')}
                id="item-bodySite"
                name="bodySite"
                data-cy="bodySite"
                type="select"
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
              </ValidatedField>
              <ValidatedField
                label={translate('hcpNphiesPortalApp.item.subSite')}
                id="item-subSite"
                name="subSite"
                data-cy="subSite"
                type="select"
              >
                <option value="R">{translate('hcpNphiesPortalApp.SubSiteEnum.R')}</option>
                <option value="L">{translate('hcpNphiesPortalApp.SubSiteEnum.L')}</option>
                <option value="U">{translate('hcpNphiesPortalApp.SubSiteEnum.U')}</option>
                <option value="D">{translate('hcpNphiesPortalApp.SubSiteEnum.D')}</option>
                <option value="A">{translate('hcpNphiesPortalApp.SubSiteEnum.A')}</option>
                <option value="P">{translate('hcpNphiesPortalApp.SubSiteEnum.P')}</option>
                <option value="I">{translate('hcpNphiesPortalApp.SubSiteEnum.I')}</option>
                <option value="E">{translate('hcpNphiesPortalApp.SubSiteEnum.E')}</option>
              </ValidatedField>
              <ValidatedField
                id="item-claim"
                name="claimId"
                data-cy="claim"
                label={translate('hcpNphiesPortalApp.item.claim')}
                type="select"
              >
                <option value="" key="0" />
                {claims
                  ? claims.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/item" replace color="info">
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

export default ItemUpdate;
